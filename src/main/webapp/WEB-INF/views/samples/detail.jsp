<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample 상세보기 - Soron</title>
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
            max-width: 80%;
            margin: 0 auto;
            background-color: white;
            padding: 3vh;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 3vh;
            padding-bottom: 1vh;
            border-bottom: 2px solid #e0e0e0;
        }
        
        .btn-group {
            display: flex;
            gap: 1vw;
        }
        
        .btn {
            padding: 1vh 2vh;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        .btn-warning {
            background-color: #ffc107;
            color: #212529;
        }
        
        .btn-warning:hover {
            background-color: #e0a800;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #545b62;
        }
        
        .detail-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 3vh;
        }
        
        .detail-table th {
            background-color: #f8f9fa;
            padding: 1.5vh;
            text-align: left;
            font-weight: bold;
            color: #333;
            border: 1px solid #ddd;
            width: 20%;
        }
        
        .detail-table td {
            padding: 1.5vh;
            border: 1px solid #ddd;
            vertical-align: top;
        }
        
        .status {
            display: inline-block;
            padding: 0.5vh 1.5vh;
            border-radius: 20px;
            font-size: 14px;
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
        
        .content-section {
            max-height: 30vh;
            overflow-y: auto;
            padding: 1vh;
            background-color: #f8f9fa;
            border-radius: 4px;
            line-height: 1.6;
        }
        
        .meta-info {
            color: #6c757d;
            font-size: 14px;
        }
        
        @media (max-width: 768px) {
            .container {
                max-width: 95%;
                padding: 2vh;
            }
            
            .header {
                flex-direction: column;
                gap: 1vh;
            }
            
            .btn-group {
                width: 100%;
                justify-content: center;
            }
            
            .detail-table th {
                width: 30%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Sample 상세보기</h1>
            <div class="btn-group">
                <a href="/samples" class="btn btn-secondary">목록으로</a>
                <a href="/samples/${sample.id}/edit" class="btn btn-warning">수정</a>
                <form method="post" action="/samples/${sample.id}/delete" style="display: inline;" 
                      onsubmit="return confirm('정말 삭제하시겠습니까?')">
                    <button type="submit" class="btn btn-danger">삭제</button>
                </form>
            </div>
        </div>
        
        <table class="detail-table">
            <tr>
                <th>ID</th>
                <td>${sample.id}</td>
            </tr>
            <tr>
                <th>제목</th>
                <td><strong>${sample.title}</strong></td>
            </tr>
            <tr>
                <th>상태</th>
                <td>
                    <span class="status ${sample.status == 'ACTIVE' ? 'active' : 'inactive'}">
                        ${sample.status == 'ACTIVE' ? '활성' : '비활성'}
                    </span>
                </td>
            </tr>
            <tr>
                <th>생성일시</th>
                <td class="meta-info">
                    ${dateUtil.formatKorean(sample.createdAt)}
                </td>
            </tr>
            <tr>
                <th>수정일시</th>
                <td class="meta-info">
                    ${dateUtil.formatKorean(sample.updatedAt)}
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty sample.content}">
                            <div class="content-section">
                                ${sample.content}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span class="meta-info">내용이 없습니다.</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
        
        <!-- API 정보 섹션 -->
        <div style="background-color: #e9ecef; padding: 2vh; border-radius: 4px; margin-top: 2vh;">
            <h4 style="margin-bottom: 1vh; color: #495057;">개발자 정보</h4>
            <div class="meta-info">
                <p><strong>REST API 엔드포인트:</strong> GET /api/samples/${sample.id}</p>
                <p><strong>Swagger 문서:</strong> <a href="/swagger-ui.html" target="_blank" style="color: #007bff;">API 문서 보기</a></p>
            </div>
        </div>
    </div>
</body>
</html>