<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? 'Sample 수정' : 'Sample 등록'} - Soron</title>
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
            max-width: 70%;
            margin: 0 auto;
            background-color: white;
            padding: 3vh;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header {
            text-align: center;
            margin-bottom: 3vh;
            padding-bottom: 1vh;
            border-bottom: 2px solid #e0e0e0;
        }
        
        .form-group {
            margin-bottom: 2vh;
        }
        
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 0.5vh;
            color: #333;
        }
        
        .required {
            color: #dc3545;
        }
        
        input[type="text"], textarea, select {
            width: 100%;
            padding: 1vh;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        input[type="text"]:focus, textarea:focus, select:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
        
        textarea {
            min-height: 15vh;
            resize: vertical;
            font-family: inherit;
        }
        
        .btn-group {
            display: flex;
            gap: 1vw;
            justify-content: center;
            margin-top: 3vh;
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
            text-align: center;
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
        
        .help-text {
            font-size: 12px;
            color: #6c757d;
            margin-top: 0.5vh;
        }
        
        .error-message {
            color: #dc3545;
            font-size: 12px;
            margin-top: 0.5vh;
        }
        
        .char-count {
            text-align: right;
            font-size: 12px;
            color: #6c757d;
            margin-top: 0.5vh;
        }
        
        .api-info {
            background-color: #e9ecef;
            padding: 2vh;
            border-radius: 4px;
            margin-top: 2vh;
        }
        
        @media (max-width: 768px) {
            .container {
                max-width: 95%;
                padding: 2vh;
            }
            
            .btn-group {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>${isEdit ? 'Sample 수정' : 'Sample 등록'}</h1>
        </div>
        
        <form id="sampleForm" method="post" action="/api/samples${isEdit ? '/' += sample.id : ''}">
            <c:if test="${isEdit}">
                <input type="hidden" name="_method" value="PUT">
            </c:if>
            
            <div class="form-group">
                <label for="title">제목 <span class="required">*</span></label>
                <input type="text" id="title" name="title" value="${isEdit ? sample.title : ''}" 
                       maxlength="255" required>
                <div class="help-text">최대 255자까지 입력 가능합니다.</div>
                <div class="char-count">
                    <span id="titleCount">${isEdit ? fn:length(sample.title) : 0}</span>/255
                </div>
            </div>
            
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" maxlength="1000" 
                          placeholder="샘플 내용을 입력하세요...">${isEdit ? sample.content : ''}</textarea>
                <div class="help-text">최대 1000자까지 입력 가능합니다.</div>
                <div class="char-count">
                    <span id="contentCount">${isEdit ? fn:length(sample.content) : 0}</span>/1000
                </div>
            </div>
            
            <div class="form-group">
                <label for="status">상태</label>
                <select id="status" name="status">
                    <option value="ACTIVE" ${(!isEdit || sample.status == 'ACTIVE') ? 'selected' : ''}>활성</option>
                    <option value="INACTIVE" ${isEdit && sample.status == 'INACTIVE' ? 'selected' : ''}>비활성</option>
                </select>
                <div class="help-text">샘플의 상태를 선택하세요.</div>
            </div>
            
            <div class="btn-group">
                <button type="submit" class="btn btn-primary">
                    ${isEdit ? '수정' : '등록'}
                </button>
                <a href="/samples" class="btn btn-secondary">취소</a>
            </div>
        </form>
        
        <!-- API 정보 섹션 -->
        <div class="api-info">
            <h4 style="margin-bottom: 1vh; color: #495057;">개발자 정보</h4>
            <div style="font-size: 12px; color: #6c757d;">
                <p><strong>REST API 엔드포인트:</strong> ${isEdit ? 'PUT' : 'POST'} /api/samples${isEdit ? '/' += sample.id : ''}</p>
                <p><strong>Swagger 문서:</strong> <a href="/swagger-ui.html" target="_blank" style="color: #007bff;">API 문서 보기</a></p>
            </div>
        </div>
    </div>
    
    <script>
        // 폼 제출 처리
        document.getElementById('sampleForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            const data = {
                title: formData.get('title'),
                content: formData.get('content'),
                status: formData.get('status')
            };
            
            // 제목 중복 확인
            checkTitleDuplication(data.title, ${isEdit ? sample.id : 'null'})
                .then(isDuplicated => {
                    if (isDuplicated) {
                        alert('이미 사용 중인 제목입니다. 다른 제목을 입력해주세요.');
                        return;
                    }
                    
                    // API 호출
                    const url = '/api/samples${isEdit ? '/' += sample.id : ''}';
                    const method = ${isEdit ? "'PUT'" : "'POST'"};
                    
                    fetch(url, {
                        method: method,
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(result => {
                        if (result.success) {
                            alert(result.message);
                            window.location.href = '/samples';
                        } else {
                            alert('오류가 발생했습니다: ' + result.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('서버 오류가 발생했습니다.');
                    });
                });
        });
        
        // 제목 중복 확인
        function checkTitleDuplication(title, excludeId) {
            const params = new URLSearchParams({ title });
            if (excludeId) {
                params.append('excludeId', excludeId);
            }
            
            return fetch('/api/samples/check-title?' + params)
                .then(response => response.json())
                .then(result => result.data)
                .catch(() => false);
        }
        
        // 글자 수 카운트
        document.getElementById('title').addEventListener('input', function() {
            document.getElementById('titleCount').textContent = this.value.length;
        });
        
        document.getElementById('content').addEventListener('input', function() {
            document.getElementById('contentCount').textContent = this.value.length;
        });
        
        // 실시간 제목 중복 확인
        let titleCheckTimeout;
        document.getElementById('title').addEventListener('input', function() {
            clearTimeout(titleCheckTimeout);
            const title = this.value.trim();
            
            if (title.length < 2) return;
            
            titleCheckTimeout = setTimeout(() => {
                checkTitleDuplication(title, ${isEdit ? sample.id : 'null'})
                    .then(isDuplicated => {
                        const titleInput = document.getElementById('title');
                        if (isDuplicated) {
                            titleInput.style.borderColor = '#dc3545';
                            titleInput.style.backgroundColor = '#f8d7da';
                        } else {
                            titleInput.style.borderColor = '#28a745';
                            titleInput.style.backgroundColor = '#d4edda';
                        }
                    });
            }, 500);
        });
    </script>
</body>
</html>