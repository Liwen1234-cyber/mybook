import os
import re
import requests
from urllib.parse import urlparse
from pathlib import Path

def download_image(url, save_path):
    try:
        response = requests.get(url, stream=True)
        response.raise_for_status()
        
        with open(save_path, 'wb') as f:
            for chunk in response.iter_content(chunk_size=8192):
                if chunk:
                    f.write(chunk)
        print(f"Successfully downloaded: {url} -> {save_path}")
    except Exception as e:
        print(f"Failed to download {url}: {str(e)}")

def process_markdown_files():
    # 创建images目录
    images_dir = Path("images")
    images_dir.mkdir(exist_ok=True)
    
    # 遍历所有目录
    for root, dirs, files in os.walk("F:\github_repository\Backend_project\JavaNote"):
        for file in files:
            if file.endswith(".md"):
                file_path = os.path.join(root, file)
                print(f"Processing: {file_path}")
                
                # 读取markdown文件
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                # 查找所有图片URL
                image_urls = re.findall(r'!\[.*?\]\((.*?)\)', content)
                image_urls.extend(re.findall(r'<img.*?src="(.*?)"', content))
                
                # 下载每个图片
                for url in image_urls:
                    if url.startswith('http'):
                        # 从URL中提取文件名
                        parsed_url = urlparse(url)
                        filename = os.path.basename(parsed_url.path)
                        if not filename:
                            filename = f"image_{hash(url)}.png"
                        
                        # 保存图片
                        save_path = images_dir / filename
                        download_image(url, save_path)

if __name__ == "__main__":
    process_markdown_files() 