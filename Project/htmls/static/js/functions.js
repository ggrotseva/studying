function fillTableRow(e) {
    var table = document.querySelector('tbody');
    var row = table.insertRow();

    var name = document.getElementById("name").value;
    var expiryDate = document.getElementById("expiryDate").value;
    var brand = document.getElementById("brand").value;
    var description = document.getElementById("description").value;
    var isOpened = document.getElementById("isOpened").value;

    row.innerHTML += `
    <tr>
        <td colspan="2">${name}</td>
        <td>${expiryDate}</td>
        <td>${brand}</td>
        <td>${description}</td>
        <td>${isOpened}</td>
    </tr>
    `;

    // name.innerHTML = document.getElementById("name").value;
    // expiryDate.innerHTML = document.getElementById("expiryDate").value;
    // brand.innerHTML = document.getElementById("brand").value;
    // description.innerHTML = document.getElementById("description").value;
    // isOpened.innerHTML = document.getElementById("isOpened").value;
}

var tableForm = document.getElementById("addProductForm");

tableForm.addEventListener('submit', fillTableRow);