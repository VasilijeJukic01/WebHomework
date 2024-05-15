<%--
  Created by IntelliJ IDEA.
  User: Vaske
  Date: 25.4.2024.
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <!-- Head -->
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light dark-theme">
        <a class="navbar-brand">Blog</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link">Home <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
    <!-- Body -->
    <body>
        <!-- Login -->
        <div class="container" id="login">
            <h2>Login</h2>
            <form action="api/users/login" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
        </div>
        <!-- Dark Mode -->
        <div style="position: fixed; top: 10px; right: 10px;">
            <input type="checkbox" id="darkThemeCheckbox" />
            <label for="darkThemeCheckbox">Dark Theme</label>
        </div>
        <!-- Posts Container -->
        <div id="posts" class="container d-none">
            <h1>Public Blog</h1>
            <ul id="postList" class="list-group"></ul>
            <button class="btn btn-primary" id="new-post-button">New Post</button>
        </div>
        <!-- New Post Container -->
        <div class="container d-none" id="new-post">
            <div class="form-group">
                <label for="author">Author</label>
                <input type="text" class="form-control" id="author" readonly/>
            </div>
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title"/>
            </div>
            <div class="form-group">
                <label for="content">Content</label>
                <textarea  class="form-control" id="content" rows="3"></textarea>
            </div>
            <button id="save-post" class="btn btn-primary">Save</button>
        </div>
        <!-- Post Details Container -->
        <div class="container d-none" id="post">
            <h2 id="postId"></h2>
            <p id="postDate"></p>
            <p id="postAuthor"></p>
            <p id="postBody"></p>
            <br>
            <h3> Comments </h3>
            <ul id="commentList"></ul>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" readonly/>
            </div>
            <div class="form-group">
                <label for="comment">Comment</label>
                <textarea type="text" class="form-control" id="comment"></textarea>
            </div>
            <button class="btn btn-primary" id="commentBtn">Comment</button>
        </div>
        <script>
            // Operations
            const fetchPosts = () => {
                document.getElementById('postList').innerHTML = "";
                fetch('/api/posts', {
                    method: 'GET'
                }).then(response => {
                    return response.json();
                }).then(posts => {
                    posts.forEach(post => createPostElements(post));
                })
            }

            let commentButtonListener = null;

            const getPostById = (id) => {
                document.getElementById("posts").className = "d-none";
                document.getElementById("post").className = "container d-block";

                fetch('/api/posts/' + id, {
                    method: 'GET'
                }).then(response => {
                    return response.json();
                }).then(post => {
                    const date = new Date(post.date)
                    document.getElementById("postId").innerText = post.title
                    document.getElementById("postDate").innerText = date.toLocaleDateString() + " " + date.toLocaleTimeString()
                    document.getElementById("postAuthor").innerText = post.author
                    document.getElementById("postBody").innerText = post.body

                    const commentBtn = document.getElementById("commentBtn");
                    if (commentButtonListener) {
                        commentBtn.removeEventListener("click", commentButtonListener);
                    }

                    commentButtonListener = () => {
                        const author = document.getElementById("name").value
                        const content = document.getElementById("comment").value
                        createComment(author, content, id);
                    }

                    commentBtn.addEventListener("click", commentButtonListener)
                    findCommentsByPost(id);
                })
            }

            const createComment = (author, content, postId) => {
                fetch('/api/comments', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        author: author,
                        content: content,
                        postId: postId
                    })
                }).then(() => {
                    document.getElementById("comment").value = "";
                    findCommentsByPost(postId);
                });
            };

            const findCommentsByPost = async (id) => {
                const list = document.getElementById("commentList");
                list.innerHTML = "";

                const response = await fetch(`/api/comments/` + id);
                const comments = await response.json();

                if (comments.length === 0) {
                    const noComments = document.createElement("p");
                    noComments.innerText = "No comments yet.";
                    list.appendChild(noComments);
                } else {
                    comments.forEach(comment => {
                        const author = document.createElement("h6");
                        author.innerText = comment.author;
                        const content = document.createElement("p");
                        content.innerText = comment.content;
                        list.append(author, content);
                    });
                }
            };

            const createPostElements = (post) => {
                const createElement = (tagName, className, text) => {
                    const element = document.createElement(tagName);
                    if (className) element.className = className;
                    if (text) element.innerText = text;
                    return element;
                };

                const createButtonEventListener = (postId) => () => getPostById(postId);

                const postsList = document.getElementById('postList');
                const li = createElement('li', 'list-group-item');
                const title = createElement('h3', 'h3', post.title);
                const author = createElement('p', 'small', "Author: " + post.author);
                const body = createElement('p', null, post.body);
                const button = createElement('button', 'btn btn-primary', 'Read More');

                button.addEventListener('click', createButtonEventListener(post.id));

                [title, body, author, button].forEach(element => li.appendChild(element));
                postsList.appendChild(li);
            };

            const toggleDarkTheme = (darkMode) => {
                const body = document.body;
                const navbar = document.querySelector('.navbar');
                if (darkMode) {
                    body.classList.add('dark-theme');
                    navbar.classList.add('dark-theme');
                } else {
                    body.classList.remove('dark-theme');
                    navbar.classList.remove('dark-theme');
                }
            };

            function onLogin(token) {

                let base64Url = token.split('.')[1];
                let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
                let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                }).join(''));

                let data = JSON.parse(jsonPayload);
                let username = data.sub;

                localStorage.setItem('username', username);

                document.getElementById('login').style.display = 'none';
                document.getElementById('posts').className = 'container d-block';
                document.getElementById('new-post').style.display = 'block';
                document.getElementById('new-post-button').style.display = 'block';
                document.getElementById('post').style.display = 'block';
                document.getElementById('name').value = username;

                fetchPosts()
            }


            // Driver
            document.getElementById("save-post").addEventListener("click", () => {
                const author = document.getElementById("author");
                const authorText = author.value;
                const title = document.getElementById("title");
                const titleText = title.value;
                const body = document.getElementById("content");
                const bodyText = body.value;

                fetch('/api/posts', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        author: authorText,
                        title: titleText,
                        body: bodyText,
                        date: new Date()
                    })
                })
                    .then(response => {
                        fetchPosts()
                        author.value = "";
                        body.value = "";
                        title.value = "";
                        document.getElementById("posts").className = "container d-block";
                        document.getElementById("new-post").className = "d-none";
                    })
            })

            document.getElementById("new-post-button").addEventListener("click", () => {
                document.getElementById("posts").className = "d-none";
                document.getElementById("new-post").className = "container d-block";
                document.getElementById("author").value = localStorage.getItem('username');
            })

            document.getElementById('darkThemeCheckbox').addEventListener('change', (event) => {
                toggleDarkTheme(event.target.checked);
            });

            toggleDarkTheme(document.getElementById('darkThemeCheckbox').checked);

            const homeLink = document.querySelector('.nav-item.active a');
            homeLink.addEventListener('click', function(event) {
                event.preventDefault();
                document.getElementById('new-post').className = 'd-none';
                document.getElementById('post').className = 'd-none';
                document.getElementById('posts').className = 'container d-block';
                fetchPosts();
            });

            window.onload = function() {
                document.getElementById('posts').style.display = 'none';
                document.getElementById('new-post').style.display = 'none';
                document.getElementById('post').style.display = 'none';
            };

            document.querySelector('#login form').addEventListener('submit', function(event) {
                event.preventDefault();

                const username = document.querySelector('#username').value;
                const password = document.querySelector('#password').value;

                const data = {
                    username: username,
                    password: password
                };

                fetch('api/users/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                }).then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error('Login failed. Please try again.');
                    }
                }).then(token => {
                    onLogin('Bearer ' + token);
                }).catch(error => {
                    console.error('Error:', error);
                });
            });
        </script>
    </body>
</html>
