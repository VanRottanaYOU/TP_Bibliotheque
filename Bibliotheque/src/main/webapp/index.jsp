<%@ page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Gestion de la bibliotheque</title>
    </head>

    <body>
        <p>***** Bibliotheque Administration *****</p>
		<p><a href="<c:url value="/ListerLivres"/>">1. Lister les livres</a></p>
		<p><a href="<c:url value="/DescriptionLivre.jsp"/>">2. Description d'un livre</a></p>
		<p><a href="<c:url value="/CreationLivre.jsp"/>">3. Création d'un livre</a></p>
		<p><a href="<c:url value="/UpdateLivre.jsp"/>">4. Mise à jour d'un livre</a></p>
		
		<p><a href="<c:url value="/ListerAuteurs"/>">1. Lister les auteurs</a></p>
		<p><a href="<c:url value="/DescriptionAuteur.jsp"/>">2. Description d'un auteur</a></p>
		<p><a href="<c:url value="/CreationAuteur.jsp"/>">3. Création d'un auteur</a></p>
		<p><a href="<c:url value="/UpdateAuteur.jsp"/>">4. Mise à jour d'un auteur</a></p>
		
<%-- 		<p><a href="<c:url value="/sortie"/>">99. Sortir</a></p> --%>
       
    </body>
</html>