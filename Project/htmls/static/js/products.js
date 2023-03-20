const backendLocation = "http://localhost:8080";

let categoryId = document.getElementById("categoryId").getAttribute("value");
let productTable = document.getElementById("tableContent");

fetch(`${backendLocation}/categories/${categoryId}/products`)
    .then((response) => response.json())
    .then((body) => {
        for (product of body) {
            fillTableRow(product);
        }
    })

function fillTableRow(product) {


    // let table = document.getElementById('#table-content');
    // let row = document.createElement('tr');

    // let nameData = document.createElement('td');
    // let expiryDateData = document.createElement('td');
    // let brandData = document.createElement('td');
    // let descriptionData = document.createElement('td');

    // nameData.textContent(document.getElementById("name").value);
    // expiryDateData.textContent(document.getElementById("expiryDate").value);
    // brandData.textContent(document.getElementById("brand").value);
    // descriptionData.textContent(document.getElementById("description").value);

    // let deleteData = document.createElement('td');

    // deleteData.innerHTML = `
    // <form action="/products/${id}" method="delete">
    //     <button type="submit" class="close" aria-label="delete">
    //         <span aria-hidden="true">&times;</span>
    //     </button>
    // </form>
    // `;

    // row.appendChild(nameData);
    // row.appendChild(expiryDateData);
    // row.appendChild(brandData);
    // row.appendChild(descriptionData);
    // row.appendChild(deleteData);

    // table.appendChild(row);
}