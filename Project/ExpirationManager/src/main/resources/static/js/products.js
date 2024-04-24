const backendLocation = 'http://localhost:8080';

const categoryId = document.getElementById('categoryId').value;

const productTable = document.getElementById('tableContent');
const productForm = document.getElementById('productForm');

const nameInputElement = document.getElementById('name');
const expiryDateInputElement = document.getElementById('expiryDate');
const brandInputElement = document.getElementById('brand');
const descriptionInputElement = document.getElementById('description');

// csrf token
const csrfHeaderName = document.getElementById('csrf').getAttribute('name');
const csrfHeaderValue = document.getElementById('csrf').getAttribute('value');

// fetch all products of category
fetch(`${backendLocation}/categories/${categoryId}/products`)
    .then(response => response.json())
    .then(products => {
        const productsFragment = document.createDocumentFragment();

        for (let product of products) {
            productsFragment.append(createTableRow(product));
        }

        productTable.append(productsFragment);
    });

// fill table row with product data
function createTableRow(product) {
    const tableRow = document.createElement('tr');

    const nameData = document.createElement('td');
    nameData.textContent = product.name;

    const dateData = document.createElement('td');
    dateData.textContent = formatDate(product.expiryDate);

    const brandData = document.createElement('td');
    brandData.textContent = product.brand;

    const descriptionData = document.createElement('td');
    descriptionData.textContent = product.description;

    const deleteButton = document.createElement('td');
    deleteButton.innerHTML = `<button class="close" aria-label="delete">
                        <span aria-hidden="true">&times;</span>
                            </button>`;

    // delete product event listener
    deleteButton.addEventListener('click', () => {
        fetch(`${backendLocation}/categories/${categoryId}/products/${product.id}`, {
            method: 'DELETE',
            headers: {
                [csrfHeaderName]: csrfHeaderValue
            }
        }).then(response => {
            if (response.ok) {
                tableRow.remove();
            }
        })
    });

    tableRow.append(nameData);
    tableRow.append(dateData);
    tableRow.append(brandData);
    tableRow.append(descriptionData);
    tableRow.append(deleteButton);

    return tableRow;
}

// productForm on submit event
productForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const name = nameInputElement.value;
    const expiryDate = expiryDateInputElement.value;

    const validName = isNameValid(name);
    const validDate = isDateValid(expiryDate);

    if (!validName || !validDate) {
        return;
    }

    const brand = brandInputElement.value;
    const description = descriptionInputElement.value;

    fetch(`${backendLocation}/categories/${categoryId}/products`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            name,
            expiryDate,
            brand,
            description
        })
    }).then(response => {
        clearInputs();

        const location = response.headers.get('Location');

        fetch(`${backendLocation}${location}`)
            .then(res => res.json())
            .then(product => createTableRow(product))
    })
})

// format date
function formatDate(dateString) {
    const date = new Date(dateString);

    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    day < 10 && (day = `0${day}`);
    month < 10 && (month = `0${month}`);

    return `${day}/${month}/${year}`;
}

// product name validation
function isNameValid(name) {
    document.getElementById('nameError').innerHTML = '';

    if (typeof name === 'undefined' || name.length == 0) {
        const nameError = document.createElement('small');
        nameError.classList.add('alert', 'alert-danger', 'p-1');
        nameError.textContent = 'Product Name is required.';
        document.getElementById('nameError').append(nameError);
        return false;
    }

    return true;
}

// product date validation
function isDateValid(expiryDate) {
    document.getElementById('dateError').innerHTML = '';

    if (typeof expiryDate === 'undefined' || expiryDate.length == 0) {
        const expiryDateError = document.createElement('small');
        expiryDateError.classList.add('alert', 'alert-danger', 'p-1');
        expiryDateError.textContent = 'Expiry Date is required.';
        document.getElementById('dateError').append(expiryDateError);
        return false;
    }

    return true;
}

function clearInputs() {
    nameInputElement.value = '';
    expiryDateInputElement.value = '';
    brandInputElement.value = '';
    descriptionInputElement.value = '';
}