function fillTableRow() {
    let table = document.getElementById('#table-content');
    let row = document.createElement('tr');

    let nameData = document.createElement('td');
    let expiryDateData = document.createElement('td');
    let brandData = document.createElement('td');
    let descriptionData = document.createElement('td');

    nameData.textContent(document.getElementById("name").value);
    expiryDateData.textContent(document.getElementById("expiryDate").value);
    brandData.textContent(document.getElementById("brand").value);
    descriptionData.textContent(document.getElementById("description").value);

    let deleteData = document.createElement('td');

    deleteData.innerHTML = `
    <form action="/products/${id}" method="delete">
        <button type="submit" class="close" aria-label="delete">
            <span aria-hidden="true">&times;</span>
        </button>
    </form>
    `;

    row.append(nameData);
    row.append(expiryDateData);
    row.append(brandData);
    row.append(descriptionData);
    row.append(deleteData);
    
    table.append(row);
}

let addButton = document.getElementById("addButton");

addButton.addEventListener("submit", fillTableRow);