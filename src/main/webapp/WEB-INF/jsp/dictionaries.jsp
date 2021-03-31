<%@ page import="java.util.HashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<jsp:directive.page contentType="text/html;charset=UTF-8"/>
<head>
    <link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css">
    <title>Dictionary</title>
</head>
<body>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
<script>
    const href = '${pageContext.request.scheme}' + '://' + '${pageContext.request.serverName}' + ':' + '${pageContext.request.serverPort}' + '/' + '${pageContext.request.contextPath}' + '/dictionary/createOrChange/';

    function createOrChange(isMainMenu) {
        let id;
        if (isMainMenu) {
            document.location.href = href + $("input[name=firstWidget]:checked")[0].id;
        } else {
            id = $("input[name=secondWidget]:checked")[0].id;
            id = id.substring(0, id.length - 1)
            document.location.href = href + id;
        }
    }

    function remove(isMainMenu) {
        let id;
        if (isMainMenu) {
            id = $("input[name=firstWidget]:checked")[0].id;
        } else {
            id = $("input[name=secondWidget]:checked")[0].id;
            id = id.substring(0, id.length - 1)
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/dictionary/' + id,
            method: 'delete',
            dataType: 'json',
            data: {text: 'Текст'},
            success: function (data) {
                location.reload()
            }
        });
    }

    function getByTranslation() {
        const translation = document.getElementById('inputValue').value;
        if (translation !== "") {
            $.ajax({
                url: '${pageContext.request.contextPath}/dictionary/' + translation,
                method: 'get',
                dataType: 'html',
                success: function (data) {
                    insertHTML(data)
                }
            });
        }
    }

    function getByTranslationAndType() {
        const translation = document.getElementById('inputValue').value;
        if (translation !== "") {
            const type = $("input[name=firstWidget]:checked")[0].defaultValue;
            if (type !== "") {
                $.ajax({
                    url: '${pageContext.request.contextPath}/dictionary/' + translation,
                    method: 'post',
                    dataType: 'html',
                    contentType: 'application/json',
                    data: type,
                    success: function (data) {
                        insertHTML(data)
                    }
                });
            }
        }
    }

    function getByWordValue() {
        const wordValue = document.getElementById('inputValue').value;
        if (wordValue !== "") {
            $.ajax({
                url: '${pageContext.request.contextPath}/dictionary/getByWordValue/' + wordValue,
                method: 'post',
                dataType: 'html',
                contentType: 'application/json',
                success: function (data) {
                    insertHTML(data)
                }
            });
        }
    }

    function insertHTML(data) {
        document.getElementById("secForm").innerHTML = data;
    }
</script>

<br/>
<input id="inputValue" type="text">
<p>поиск по значению во всех словарях</p>
<button onclick="getByTranslation()">find</button>
<p>поиск по значению в выбранном словаре</p>
<button onclick="getByTranslationAndType()">find</button>
<p>поиск по ключу в выбранном словаре</p>
<button onclick="getByWordValue()">find</button>
<c:forEach var="dictionary" items="${dictionaries.entrySet()}">
    <section class="ac-container" id=${dictionary.getKey()}>
        <c:forEach var="word" items="${dictionary.getValue()}">

            <input id="${word.getId()}" name="firstWidget" value="${word.getType()}" type="radio" checked/>
            <label content="${word.getType()}" for="${word.getId()}">${word.value}</label>
            <article>
                <p>
                    <c:forEach var="value" items="${word.getKeys()}">
                        "${value.value}"
                    </c:forEach>
                </p>
            </article>

        </c:forEach>
    </section>
</c:forEach>
<section class="ac-container">

    <input id="${0}" name="firstWidget" type="radio" checked/>
    <label for="${0}">новый ключ</label>
    <article>
        <p>
            "пусто"
        </p>
    </article>

</section>
<button onclick='createOrChange(true)'>edit/add</button>
<button type="button" class="button-class" onclick="remove(true)" value="delete">delete</button>
<div id="secForm"></div>
</body>
</html>
