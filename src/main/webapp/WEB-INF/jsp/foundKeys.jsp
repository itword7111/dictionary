<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:jsp="http://java.sun.com/JSP/Page">

<jsp:directive.page contentType="text/html;charset=UTF-8"/>

<script>
    var selected2 = 0;
</script>

<div id="secForm"/>

<c:forEach var="dictionary" items="${keys.entrySet()}">
    <section class="ac-container" id="${dictionary.getKey()}s">
        <c:forEach var="word" items="${dictionary.getValue()}">
            <div onclick='javascript: selected2=${word.getId()};selected=0;'>
                <input id="${word.getId()}s" name="accordion-1" type="radio" checked/>
                <label for="${word.getId()}s">${word}</label>
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
<button onclick='javascript: document.location.href = "http://localhost:8080/dictionary_war_exploded/dictionary/createOrChange/"+selected2;'>
    edit/add
</button>
<button type="button" class="button-class" onclick="remove(selected2)" value="delete">delete found word</button>

</html>