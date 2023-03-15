let backendLocation = "https://localhost:8080";

let routeId = document.getElementById("routeId").getAttribute("value");
let commentSection = document.getElementById("commentCtnr");

fetch(`${backendLocation}/api/${routeId}/comments`)
    .then((response) => response.json())
    .then((body) => {
        for (comment of body) {

        }
    })

function addCommentAsHtml(comment) {
    let commentHtml = '<div class="comments">\n';
    commentHtml += '<div hidden>' + comment.id + '</div>\n';
    commentHtml += '<h4>' + comment.authorName + '</h4>\n';
    commentHtml += '<p>' + comment.textContent + '</p>\n';
    commentHtml += '<span>' + comment.created + '</span>\n';
    commentHtml += '</div>\n';

    commentSection.innerHTML = commentHtml;
}

let csrfHeaderName = document.getElementById("csrf").getAttribute("name");
let csrfHeaderToken = document.getElementById("csrf").getAttribute("value");

let commentForm = document.getElementById("commentForm");

commentForm.addEventListener("submit", (event) => {
    event.preventDefault;

    let text = document.getElementById("message").value;

    fetch(`${backendLocation}/api/${routeId}/comments`, {
        method: "post",
        headres: {
            "Content-type": "application/json",
            "Accept": "application/json",
            [csrfHeaderName]: csrfHeaderToken
        },
        body: JSON.stringify({
            textContent: text
        })
    }).then((res) => {
        document.getElementById("message").value = "";

        let location = res.headers.get("Location");
        fetch(`${backendLocation}${location}`, {
            
        })
    })
})
