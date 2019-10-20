<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Product</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<h1 class="display-4">${product.name}</h1>
		</div>
		<div class="row">
			<div class="col border">
				<h3 class="font-weight-normal mb-3">Products:</h3>
				<ul>
				<c:forEach items="${productCats}" var="category">
					<li>${category.name}</li>
				</c:forEach>
				</ul>
			</div>
			<div class="col border">
				<h1 class="font-weight-normal mb-3">Add New Product:</h1>
				<form:form action="/products/${product.id}" method="post" modelAttribute="product">
					<input type="hidden" name="_method" value="put">
					<select name="categoryID">
						<c:forEach items="${availableCategories}" var="otherCategory">
							<option value="${otherCategory.id}">${otherCategory.name}</option>
						</c:forEach>
					</select>
					<form:button type="submit">Add</form:button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>