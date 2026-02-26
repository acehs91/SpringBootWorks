/**
 * MVP-DevMatch 메인 자바스크립트
 */

// DOM 로드 완료 후 실행
document.addEventListener('DOMContentLoaded', function() {
    console.log('DevMatch loaded!');
    
    // 부트스트랩 툴팁 초기화
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    if (tooltipTriggerList.length > 0) {
        [...tooltipTriggerList].map(el => new bootstrap.Tooltip(el));
    }
});

/**
 * 폼 유효성 검사
 */
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return false;
    }
    return true;
}

/**
 * 알림 표시
 */
function showAlert(message, type = 'info') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const container = document.querySelector('.container');
    if (container) {
        container.insertBefore(alertDiv, container.firstChild);
    }
}
