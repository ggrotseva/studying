function fillTableRow() {
    var table = document.getElementById('table-content');
    var row = table.insertRow();

    var name = document.getElementById("name").value;
    var expiryDate = document.getElementById("expiryDate").value;
    var brand = document.getElementById("brand").value;
    var description = document.getElementById("description").value;
    var isOpened = document.getElementById("isOpened").value;

    table.innerHTML += `
    <tr>
        <td>${name}</td>
        <td>${expiryDate}</td>
        <td>${brand}</td>
        <td>${description}</td>
        <td>${isOpened}</td>
        <td>
            <form action="/products/{id}" method="delete">
                <button type="submit" class="close" aria-label="delete">
                    <span aria-hidden="true">&times;</span>
                </button>
            </form>
        </td>
    </tr>
    `;

    // name.innerHTML = document.getElementById("name").value;
    // expiryDate.innerHTML = document.getElementById("expiryDate").value;
    // brand.innerHTML = document.getElementById("brand").value;
    // description.innerHTML = document.getElementById("description").value;
    // isOpened.innerHTML = document.getElementById("isOpened").value;
}


function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("categoryTable");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}


const bullet = "\u2022";
const bulletWithSpace = `${bullet} `;
const enter = 13;

const formatInputWithBullets = (event) => {
    const { keyCode, target } = event;
    const { selectionStart, value } = target;

    if (keyCode === enter) {
        console.log('a');
        target.value = [...value]
            .map((c, i) => i === selectionStart - 1
                ? `\n${bulletWithSpace}`
                : c
            )
            .join('');
        console.log(target.value);

        target.selectionStart = selectionStart + bulletWithSpace.length;
        target.selectionEnd = selectionStart + bulletWithSpace.length;
    }

    if (value[0] !== bullet) {
        target.value = `${bulletWithSpace}${value}`;
    }
}


