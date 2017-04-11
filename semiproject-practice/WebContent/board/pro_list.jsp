<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/mystyle.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
function writeList(){
	location.href="${pageContext.request.contextPath}/board/pro_write.jsp";
}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchBtn").click(function() {
			var searchTxt = document.getElementById("searchTxt").value;
			if (searchTxt == "") {
				alert("검색어를 입력하세요.");
				return;
			} else {
				var type = document.getElementById("search").value;
				location.href = "${pageContext.request.contextPath}/DispatcherServlet?command=proSearch&type=" + type + "&searchTxt=" + searchTxt;
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="/layout/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<div class="panel panel-success">
					<div class="panel-heading" align="center">건의사항 게시판</div>
					<div class="panel-body">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>글번호</th>
									<th width="50%">제 목</th>
									<th>작성자</th>
									<th>작성일</th>
									<th>조회수</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="bvo" items="${requestScope.lvo.list}">
									<tr>
										<td  align="center">${bvo.boardNo }</td>
										<td  align="center">
											<a href="${pageContext.request.contextPath}/DispatcherServlet?command=proShowContent&boardNo=${bvo.boardNo }">
											 ${bvo.title }</a>
											</td>
										<td  align="center">${bvo.member.name }</td>
										<td  align="center">${bvo.timePosted }</td>
										<td  align="center">${bvo.hits }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<p class="paging" align="center">
						<!-- 코드량을 줄이기 위해 pb 변수에 페이징 빈을 담는다 -->
						<c:set var="pb" value="${requestScope.lvo.pagingBean}"></c:set>
						<c:if test="${pb.previousPageGroup}">
							<a
								href="DispatcherServlet?command=proList&pageNo=${pb.startPageOfPageGroup-1}">◀&nbsp;
							</a>
						</c:if>
						<c:forEach var="pageNo" begin="${pb.startPageOfPageGroup}"
							end="${pb.endPageOfPageGroup}">
							<c:choose>
								<c:when test="${pb.nowPage!=pageNo}">
									<a href="DispatcherServlet?command=proList&pageNo=${pageNo}">${pageNo}</a>
								</c:when>
								<c:otherwise>
	${pageNo}
	</c:otherwise>
							</c:choose>
	&nbsp;
	</c:forEach>
						<c:if test="${pb.nextPageGroup}">
							<a
								href="DispatcherServlet?command=proList&pageNo=${pb.endPageOfPageGroup+1}">▶</a>
						</c:if>
					</p>
					<div align="right">
						<a href="#"><img class="action"
							src="${pageContext.request.contextPath}/img/write_btn.jpg"
							onclick="writeList()"></a>
						<br><br>
					</div>
					<div class="panel-footer" align="center">
						<select id="search">
							<option value="title">제목</option>
							<option value="titleAndContent">제목+내용</option>
							<option value="writer">작성자</option>
						</select>
						<input type="text" id="searchTxt" placeholder="Search" size="30">
						<button type="button" id="searchBtn" class="btn btn-default">검색</button>
					</div>
				</div>
			</div>

		</div>
	</div>
	<div class="container">
		<div class="row"></div>
	</div>
	<jsp:include page="/layout/footer.jsp" />
</body>
</html>