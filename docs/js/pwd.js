(function () {
    //依赖https://github.com/brix/crypto-js
    if (!window.$docsify) {
        return;
    }

    function parsePwd(content) {
        // Get the URL parameters
        let currentURL = window.location.href;
        const hashParams = currentURL.split('?')[1]; // 获取问号后面的部分
        var urlParams = new URLSearchParams(hashParams);
        var pwdParam = urlParams.get('pwd');
        // Replace <pwd> and <tip> tags based on password validation
        return content.replace(/<pwd\s+(.*?)>([\s\S]*?)<\/pwd>/g, function (match, attributes, pwdContent) {
            // 匹配 pwd 标签中的各个属性
            const regex = /(\w+)=['"](.*?)['"]/g;
            let attrMatch;
            let password = '';
            let pwd = '';
            let urlContent = '';

            // 提取属性值
            while ((attrMatch = regex.exec(attributes)) !== null) {
                const attrName = attrMatch[1];
                const attrValue = attrMatch[2];
                if (attrName === 'value') password = attrValue;
                if (attrName === 'pwd') pwd = attrValue;
                if (attrName === 'url') urlContent = attrValue;
            }
            if (pwdParam === password) {
                return pwdContent; // 如果 URL 参数匹配，则显示 <pwd> 标签内容
            } else {
                if (pwdParam) {
                    alert("密码错误,请重新输入")
                }
                let tipContent = '<p style="text-align: center;"><img src="' + urlContent + '" data-origin="http://niubility.cloud/image/rui.jpg" alt=""/></p><p style="text-align: center;"><strong>人机验证，扫码回复：' + pwd + '</strong></p><p style="text-align: center;"><input id ="pwd" /> <button onclick="addParamAndRefresh()">验证</button></p>';
                console.log(tipContent)
                return tipContent; // 否则显示提示内容
            }
        });
    }

    // Parse <pwd> tags in the page content
    function parseContent(content) {
        return parsePwd(content);
    }

    const afterEachHook = function (hook, vm) {
        hook.beforeEach(function (html, next) {
            next(parseContent(html))
        });
    }
    window.$docsify.plugins = [afterEachHook].concat(window.$docsify.plugins || []);
})();

// 点击按钮时执行的函数
function addParamAndRefresh() {
    // 获取输入框元素
    let inputElement = document.getElementById('pwd');
    // 获取输入框的值
    let inputValue = inputElement.value;
    if (!inputValue) {
        return
    }
    let currentURL = window.location.href;
    const hashParams = currentURL.split('?')[1]; // 获取问号后面的部分
    var urlParams = new URLSearchParams(hashParams);
    // 检查是否存在名为 'pwd' 的参数
    if (urlParams.has('pwd')) {
        // 修改 'pwd' 参数的值为 '1'
        urlParams.set('pwd', inputValue);
    } else {
        // 如果 'pwd' 参数不存在，则添加它
        urlParams.append('pwd', inputValue);
    }
    // 获取更新后的参数字符串
    var newParams = urlParams.toString();

    // 更新当前页面URL中的参数部分
    if (newParams) {
        currentURL = currentURL.split('?')[0] + '?' + newParams;
    } else {
        currentURL = currentURL.split('?')[0];
    }

    // 刷新页面并跳转到新的URL
    // 改变 hash 路由
    // 使用 Docsify 提供的 router.go() 方法进行路由导航
    //window.$docsify.router.go(currentURL);
    window.location.href = currentURL;
}