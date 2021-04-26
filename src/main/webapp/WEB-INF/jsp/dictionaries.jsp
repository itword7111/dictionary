<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<jsp:directive.page contentType="text/html;charset=UTF-8"/>
<jsp:directive.page import="com.example.dictionary.model.TypeOfDictionary" />

<head>
    <link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css">
    <title>Dictionary</title>
</head>
<body>
<script src="<c:url value="/js/jquery-1.10.2.min.js" />" type="text/javascript"></script>
<script>
function getCheckedInputId(isMainMenu){
    let id;
    if (isMainMenu) {
        id = $("input[name=firstWidget]:checked")[0].id;
    } else {
        id = $("input[name=secondWidget]:checked")[0].title;
    }
    if(id==="null"){
        return "";
    }
    return id;
}
    function createOrChange(isMainMenu) {
        window.location.href='${pageContext.request.contextPath}/dictionary/createOrChange/?id='+getCheckedInputId(isMainMenu);
    }
function remove(isMainMenu){
    const selected = getCheckedInputId(isMainMenu);
    $.ajax({
        url: '${pageContext.request.contextPath}/dictionary/deleteWord/',
        method: 'post',
        data:{
            id: selected
        },
        success: function(){
            location.reload()
        }
    });
}

    function getByTranslation() {
        const translation = document.getElementById('inputValue').value;
        if (translation !== "") {
            $.ajax({
                url: '${pageContext.request.contextPath}/dictionary/' ,
                method: 'post',
                dataType: 'html',
                data:{
                    translation: translation,
                    type: null,
                    value: null
                },
                success: function (data) {
                    insertHTML(data)
                }
            });
        }
    }

    function getByTranslationAndType() {
        const translation = document.getElementById('inputValue').value;
        if (translation !== "") {


                $.ajax({
                    url: '${pageContext.request.contextPath}/dictionary/' ,
                    method: 'post',
                    dataType: 'html',
                    data:{
                        translation: translation,
                        type: document.getElementById("wordType").value,
                        value: null
                    },
                    success: function (data) {
                        insertHTML(data)
                    }
                });

        }
    }

    function getByWordValue() {
        const wordValue = document.getElementById('inputValue').value;
        if (wordValue !== "") {
            $.ajax({
                url: '${pageContext.request.contextPath}/dictionary/' ,
                method: 'post',
                dataType: 'html',
                data:{
                    translation: null,
                    type: null,
                    value: wordValue
                },
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
<p>выбор словаря</p>
<select id="wordType">
    <option value="LATIN4" >LATIN4</option>
    <option value="ARAB5" >ARAB5</option>
</select>
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
            <label for="${word.getId()}" >${word.value}</label>
            <article>
                <p>
                    <c:forEach var="value" items="${word.getTranslations()}">
                        "${value.value}"
                    </c:forEach>
                </p>
            </article>

        </c:forEach>
    </section>
</c:forEach>
<section class="ac-container">

    <input id="null" name="firstWidget" type="radio" checked/>
    <label for="null">новый ключ</label>
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
