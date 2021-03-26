<%@ page import="java.util.HashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style><%@include file="/WEB-INF/jsp/style.css"%></style>
<html>

<jsp:directive.page contentType="text/html;charset=UTF-8" />
<head>
    <title>Dictionary</title></head>
<body>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>//скачать
<script>
    var selected=0;
    function remove(id){
        $.ajax({
            url: '/dictionary_war_exploded/dictionary/' + id,
            method: 'delete',
            dataType: 'json',
            data: {text: 'Текст'},
            success: function(data){
                location.reload()
            }
        });
    }
    function findByValue(value){
        $.ajax({
            url: '/dictionary_war_exploded/dictionary/' + value,
            method: 'get',
            dataType: 'html',
            success: function(data){
                insertHTML(data)
            }
        });
    }
    function post(){
        $.ajax({
            url: '/dictionary_war_exploded/dictionary/',
            method: 'post',
            dataType: 'json',
            data: {text: 'Текст'},
            body:{
                value: 'value',
                type: 'type',
                keys: []
            },
            success: function(data){

            }
        });
    }

    function insertHTML(data){
        var doc=document.getElementById("secForm");
        doc.insertAdjacentHTML('afterend', data);
    }
</script>

<br/>
<input id="inputValue" type="text">
<button onclick="findByValue(document.getElementById('inputValue').value)">find</button>
<c:forEach var="dictionary" items="${hashMap.entrySet()}">
<section class="ac-container" id=${dictionary.getKey()}>
    <c:forEach var="word" items="${dictionary.getValue()}">
    <div onclick='javascript: selected=${word.getId()}'>
        <input id="${word.getId()}" name="accordion-1" type="radio" checked />
        <label for="${word.getId()}">${word}</label>
        <article>
            <p>
            <c:forEach var="value" items="${word.getKeys()}">
            "${value}"
            </c:forEach>
        </p>
        </article>
    </div>
    </c:forEach>
</section>
</c:forEach>
<section class="ac-container" >
    <div onclick='javascript: selected=0'>
    <input id="${0}" name="accordion-1" type="radio" checked />
    <label for="${0}">новый ключ</label>
    <article>
        <p>
                "пусто"
        </p>
    </article>
</div>
</section>
<button onclick='javascript: document.location.href = "http://localhost:8080/dictionary_war_exploded/dictionary/createOrChange/"+selected;'>edit/add</button>
<button type="button" class="button-class" onclick="remove(selected)"  value="delete">delete</button>
<div id="secForm"/>
</body>
</html>
