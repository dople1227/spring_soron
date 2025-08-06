<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample 목록 - Soron</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            padding: 2vh;
        }
        
        .container {
            max-width: 90%;
            margin: 0 auto;
            background-color: white;
            padding: 2vh;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2vh;
            padding-bottom: 1vh;
            border-bottom: 2px solid #e0e0e0;
        }
        
        .search-form {
            background-color: #f8f9fa;
            padding: 1.5vh;
            border-radius: 6px;
            margin-bottom: 2vh;
        }
        
        .search-row {
            display: flex;
            gap: 1vw;
            align-items: center;
            flex-wrap: wrap;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
            min-width: 15%;
        }
        
        label {
            font-weight: bold;
            margin-bottom: 0.5vh;
            color: #333;
        }
        
        input, select, button {
            padding: 1vh;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        
        button {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        button:hover {
            background-color: #0056b3;
        }
        
        .btn-success {
            background-color: #28a745;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .table-container {
            overflow-x: auto;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 2vh;
        }
        
        th, td {
            padding: 1vh;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #333;
        }
        
        tr:hover {
            background-color: #f5f5f5;
        }
        
        .status {
            padding: 0.5vh 1vh;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .status.active {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status.inactive {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .btn-group {
            display: flex;
            gap: 0.5vw;
        }
        
        .btn-sm {
            padding: 0.5vh 1vh;
            font-size: 12px;
        }
        
        .btn-primary {
            background-color: #007bff;
        }
        
        .btn-warning {
            background-color: #ffc107;
            color: #212529;
        }
        
        .btn-danger {
            background-color: #dc3545;
        }
        
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 1vw;
            margin-top: 2vh;
        }
        
        .page-info {
            color: #6c757d;
        }
        
        .alert {
            padding: 1vh;
            margin-bottom: 2vh;
            border-radius: 4px;
        }
        
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        @media (max-width: 768px) {
            .search-row {
                flex-direction: column;
            }
            
            .form-group {
                min-width: 100%;
            }
            
            .btn-group {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Sample 목록</h1>
            <a href="/samples/new" class="btn-success" style="text-decoration: none; padding: 1vh 2vh; border-radius: 4px;">새 샘플 등록</a>
        </div>
        
        <!-- 성공/오류 메시지 표시 -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        
        <!-- 검색 폼 -->
        <form method="get" action="/samples" class="search-form">
            <div class="search-row">
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" id="title" name="title" value="${searchDto.title}" placeholder="제목 검색">
                </div>
                <div class="form-group">
                    <label for="content">내용</label>
                    <input type="text" id="content" name="content" value="${searchDto.content}" placeholder="내용 검색">
                </div>
                <div class="form-group">
                    <label for="status">상태</label>
                    <select id="status" name="status">
                        <option value="">전체</option>
                        <option value="ACTIVE" ${searchDto.status == 'ACTIVE' ? 'selected' : ''}>활성</option>
                        <option value="INACTIVE" ${searchDto.status == 'INACTIVE' ? 'selected' : ''}>비활성</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="size">페이지 크기</label>
                    <select id="size" name="size">
                        <option value="10" ${searchDto.size == 10 ? 'selected' : ''}>10개</option>
                        <option value="20" ${searchDto.size == 20 ? 'selected' : ''}>20개</option>
                        <option value="50" ${searchDto.size == 50 ? 'selected' : ''}>50개</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>&nbsp;</label>
                    <button type="submit">검색</button>
                </div>
            </div>
        </form>
        
        <!-- 샘플 목록 테이블 -->
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>제목</th>
                        <th>내용</th>
                        <th>상태</th>
                        <th>생성일</th>
                        <th>수정일</th>
                        <th>작업</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty samples.content}">
                            <tr>
                                <td colspan="7" style="text-align: center; padding: 3vh; color: #6c757d;">
                                    등록된 샘플이 없습니다.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="sample" items="${samples.content}">
                                <tr>
                                    <td>${sample.id}</td>
                                    <td>
                                        <a href="/samples/${sample.id}" style="text-decoration: none; color: #007bff;">
                                            ${sample.title}
                                        </a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:length(sample.content) > 50}">
                                                ${fn:substring(sample.content, 0, 50)}...
                                            </c:when>
                                            <c:otherwise>
                                                ${sample.content}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span class="status ${sample.status == 'ACTIVE' ? 'active' : 'inactive'}">
                                            ${sample.status == 'ACTIVE' ? '활성' : '비활성'}
                                        </span>
                                    </td>
                                    <td>
                                        ${dateUtil.format(sample.createdAt)}
                                    </td>
                                    <td>
                                        ${dateUtil.format(sample.updatedAt)}
                                    </td>
                                    <td>
                                        <div class="btn-group">
                                            <a href="/samples/${sample.id}" class="btn-sm btn-primary" style="text-decoration: none; color: white;">보기</a>
                                            <a href="/samples/${sample.id}/edit" class="btn-sm btn-warning" style="text-decoration: none; color: #212529;">수정</a>
                                            <form method="post" action="/samples/${sample.id}/delete" style="display: inline;" 
                                                  onsubmit="return confirm('정말 삭제하시겠습니까?')">
                                                <button type="submit" class="btn-sm btn-danger">삭제</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        
        <!-- 페이징 -->
        <c:if test="${samples.totalPages > 1}">
            <div class="pagination">
                <c:if test="${!samples.first}">
                    <a href="?page=0&size=${searchDto.size}&title=${searchDto.title}&content=${searchDto.content}&status=${searchDto.status}" 
                       style="text-decoration: none; padding: 0.5vh 1vh; background-color: #007bff; color: white; border-radius: 4px;">처음</a>
                    <a href="?page=${samples.page - 1}&size=${searchDto.size}&title=${searchDto.title}&content=${searchDto.content}&status=${searchDto.status}" 
                       style="text-decoration: none; padding: 0.5vh 1vh; background-color: #007bff; color: white; border-radius: 4px;">이전</a>
                </c:if>
                
                <span class="page-info">
                    ${samples.page + 1} / ${samples.totalPages} 페이지 (총 ${samples.totalElements}개)
                </span>
                
                <c:if test="${!samples.last}">
                    <a href="?page=${samples.page + 1}&size=${searchDto.size}&title=${searchDto.title}&content=${searchDto.content}&status=${searchDto.status}" 
                       style="text-decoration: none; padding: 0.5vh 1vh; background-color: #007bff; color: white; border-radius: 4px;">다음</a>
                    <a href="?page=${samples.totalPages - 1}&size=${searchDto.size}&title=${searchDto.title}&content=${searchDto.content}&status=${searchDto.status}" 
                       style="text-decoration: none; padding: 0.5vh 1vh; background-color: #007bff; color: white; border-radius: 4px;">마지막</a>
                </c:if>
            </div>
        </c:if>
    </div>
</body>
</html>