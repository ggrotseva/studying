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
    productHtml += '<td>' + formatDate(product.expiryDate) + '</td>\n';
    productHtml += '<td>' + product.brand + '</td>\n';
    productHtml += '<td>' + product.description + '</td>\n';
    // productHtml += `<td><img class="close" src="../static/images/edit.svg" alt="edit" onclock="editProduct(${product.id})"></td>\n`;
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

function isNameValid(name) {
    document.getElementById("nameError").innerHTML = "";
    if (typeof name === "undefined" || name.length == 0) {
        let nameError = document.createElement("small");
        nameError.setAttribute("class", "alert alert-danger p-1");
        nameError.innerHTML = "Product Name is required.";
        document.getElementById("nameError").appendChild(nameError);
        return false;
    }
    return true;
}

function isDateValid(expiryDate) {
    document.getElementById("dateError").innerHTML = "";
    if(typeof expiryDate === "undefined" || expiryDate.length == 0) {
        let expiryDateError = document.createElement("small");
        expiryDateError.setAttribute("class", "alert alert-danger p-1");
        expiryDateError.innerHTML = "Expiry Date is required.";
        document.getElementById("dateError").appendChild(expiryDateError);
        return false;
    }
    return true;
}

// productForm on submit event
productForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let nameInputElement = document.getElementById("name");
    let expiryDateInputElement = document.getElementById("expiryDate");
    console.log(expiryDateInputElement.value);

    let validName = isNameValid(nameInputElement.value);
    let validDate = isDateValid(expiryDateInputElement.value);

    if (!validName || !validDate) {
        throw new Error(`Invalid input`);
    }

    let nameInput = nameInputElement.value;
    let expiryDateInput = expiryDateInputElement.value;
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
}).catch(error => console.log(error))


// format date
function formatDate(dateString) {
    let date = new Date(dateString);

    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    day < 10 && (day = `0${day}`);
    month < 10 && (month = `0${month}`);

    return `${day}/${month}/${year}`;
}

// example function for error messages with REST controllers

// function checkTitle(event) {
//     let recipeTitle = event.target.value;

//     if (recipeTitle.length > 2) {

//         let titleErrorElement = document.getElementById("title-error");

//         titleErrorElement.replaceChildren();

//         fetch(`/api/recipes/isAvailable/${recipeTitle}`)
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error(`HTTP error! Status: ${response.status}`);
//                 }
//                 return response.json()
//             })
//             .then(isAvailable => {
//                 if (isAvailable === false) {
//                     let listElement = document.createElement("ul");
//                     let itemElement = document.createElement("li");
//                     itemElement.innerText = "You already have recipe with the same title.";
//                     listElement.appendChild(itemElement);
//                     titleErrorElement.appendChild(listElement);
//                 }
//             })
//             .catch(error => console.log(error));
//     }
// }

// document.getElementById("title-input").addEventListener("change", checkTitle);