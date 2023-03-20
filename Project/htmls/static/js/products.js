const backendLocation = "http://localhost:8080";

let categoryId = document.getElementById("categoryId").getAttribute("value");
let productTable = document.getElementById("tableContent");

// fetch all products of category
fetch(`${backendLocation}/categories/${categoryId}/products`)
    .then((response) => response.json())
    .then((body) => {
        for (product of body) {
            fillTableRow(product);
        }
    })

// fill table row with product data
function fillTableRow(product) {
    let productHtml = `<tr id="product${product.id}">\n`;
    productHtml += '<td>' + product.name + '</td>\n';
    productHtml += '<td>' + product.expiryDate + '</td>\n';
    productHtml += '<td>' + product.brand + '</td>\n';
    productHtml += '<td>' + product.description + '</td>\n';
    productHtml += `<td>\n
                        <button onclick="deleteProduct(${product.id})" class="close" aria-label="delete">\n
                            <span aria-hidden="true">&times;</span>\n
                        </button>\n
                    </td>\n`
    productHtml += '</tr>\n';

    productTable.innerHTML += productHtml;
}

// csrf token
const csrfHeaderName = document.getElementById('csrf').getAttribute('name');
const csrfHeaderValue = document.getElementById('csrf').getAttribute('value');

// delete product
function deleteProduct(productId) {
    fetch(`${backendLocation}/categories/${categoryId}/products/${productId}`, {
    method: 'DELETE',
         headers: {
            [csrfHeaderName]: csrfHeaderValue
         }
    })
    .then(document.getElementById("product" + productId).remove());
}

// add product form element
let productForm = document.getElementById("productForm");

// productForm on submit event
productForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let nameInput = document.getElementById("name").value;
    let expiryDateInput = document.getElementById("expiryDate").value;
    let brandInput = document.getElementById("brand").value;
    let descriptionInput = document.getElementById("description").value;

    fetch(`${backendLocation}/categories/${categoryId}/products`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            name: nameInput,
            expiryDate: expiryDateInput,
            brand: brandInput,
            description: descriptionInput
        })
    }).then((response) => {
        document.getElementById("name").value = "";
        document.getElementById("expiryDate").value = "";
        document.getElementById("brand").value = "";
        document.getElementById("description").value = "";

        let location = response.headers.get("Location");
        fetch(`${backendLocation}${location}`)
            .then(res => res.json())
            .then(product => fillTableRow(product))
    })
})
