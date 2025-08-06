<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 - Soron</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        
        .error-container {
            max-width: 60%;
            margin: 0 auto;
            background-color: white;
            padding: 4vh;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .error-icon {
            font-size: 4rem;
            color: #dc3545;
            margin-bottom: 2vh;
        }
        
        .error-title {
            color: #dc3545;
            font-size: 2rem;
            margin-bottom: 2vh;
            font-weight: bold;
        }
        
        .error-message {
            color: #6c757d;
            font-size: 1.2rem;
            margin-bottom: 3vh;
            line-height: 1.6;
        }
        
        .error-details {
            background-color: #f8f9fa;
            padding: 2vh;
            border-radius: 4px;
            margin-bottom: 3vh;
            text-align: left;
        }
        
        .error-code {
            font-family: 'Courier New', monospace;
            color: #dc3545;
            font-weight: bold;
        }
        
        .btn-group {
            display: flex;
            gap: 1vw;
            justify-content: center;
        }
        
        .btn {
            padding: 1vh 3vh;
            border: none;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #545b62;
        }
        
        .help-section {
            margin-top: 3vh;
            padding-top: 2vh;
            border-top: 1px solid #e0e0e0;
        }
        
        .help-title {
            color: #495057;
            margin-bottom: 1vh;
        }
        
        .help-text {
            color: #6c757d;
            font-size: 14px;
            line-height: 1.5;
        }
        
        @media (max-width: 768px) {
            .error-container {
                max-width: 95%;
                padding: 3vh;
            }
            
            .error-title {
                font-size: 1.5rem;
            }
            
            .error-message {
                font-size: 1rem;
            }
            
            .btn-group {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        
        <h1 class="error-title">오류가 발생했습니다</h1>
        
        <p class="error-message">
            요청을 처리하는 중에 문제가 발생했습니다.
        </p>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-details">
                <h4>오류 상세 정보:</h4>
                <p class="error-code">${errorMessage}</p>
            </div>
        </c:if>
        
        <div class="btn-group">
            <a href="/samples" class="btn btn-primary">샘플 목록으로</a>
            <a href="javascript:history.back()" class="btn btn-secondary">이전 페이지로</a>
        </div>
        
        <div class="help-section">
            <h4 class="help-title">도움이 필요하신가요?</h4>
            <div class="help-text">
                <p>지속적으로 문제가 발생한다면 다음을 확인해보세요:</p>
                <ul style="text-align: left; margin: 1vh 0; padding-left: 2vh;">
                    <li>입력한 정보가 올바른지 확인</li>
                    <li>네트워크 연결 상태 확인</li>
                    <li>브라우저 새로고침 후 다시 시도</li>
                    <li>문제가 지속되면 관리자에게 문의</li>
                </ul>
                <p style="margin-top: 2vh;">
                    <strong>API 문서:</strong> 
                    <a href="/swagger-ui.html" target="_blank" style="color: #007bff;">Swagger UI</a>에서 API 사용법을 확인할 수 있습니다.
                </p>
            </div>
        </div>
    </div>
</body>
</html>