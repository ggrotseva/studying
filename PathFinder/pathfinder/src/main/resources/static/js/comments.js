let backendLocation = "http://localhost:8080";

let routeId = document.getElementById("routeId").getAttribute("value");
let commentSection = document.getElementById("commentCtnr");

fetch(`${backendLocation}/api/${routeId}/comments`)
    .then((response) => response.json())
    .then((body) => {
        for (comment of body) {
            addCommentAsHtml(comment);
        }
    })

function addCommentAsHtml(comment) {
    let commentHtml = `<div class="comments pt-3" id="comment${comment.id}">\n`;
    commentHtml += '<div hidden>' + comment.id + '</div>\n';
    commentHtml += '<h4>' + comment.authorUsername + '</h4>\n';
    commentHtml += '<p>' + comment.textContent + '</p>\n';
    commentHtml += '<span>' + comment.created + '</span>\n';
    commentHtml += `<button class="btn btn-danger" onclick="deleteComment(${comment.id})">Delete</button>\n`
    commentHtml += '</div>\n';

    commentSection.innerHTML += commentHtml;
}

function deleteComment(commentId) {
    fetch(`${backendLocation}/api/${routeId}/comments/${commentId}`, {
    method: 'DELETE',
         headers: {
            [csrfHeaderName]: csrfHeaderValue
         }
    })
    .then(document.getElementById("comment" + commentId).remove());
}

const csrfHeaderName = document.getElementById('csrf').getAttribute('name');
const csrfHeaderValue = document.getElementById('csrf').getAttribute('value');

let commentForm = document.getElementById("commentForm");

commentForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let text = document.getElementById("message").value;

    fetch(`${backendLocation}/api/${routeId}/comments`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            message: text
        })
    }).then((res) => {
        document.getElementById("message").value = "";
        let location = res.headers.get("Location");
        fetch(`${backendLocation}${location}`)
            .then(res => res.json())
            .then(body => addCommentAsHtml(body))
    })
})
