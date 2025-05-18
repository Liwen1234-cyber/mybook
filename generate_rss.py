import os
import re
from datetime import datetime
from pathlib import Path
import frontmatter
import pytz

def get_file_metadata(file_path):
    """解析markdown文件的元数据"""
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
        
        # 尝试解析frontmatter
        try:
            post = frontmatter.loads(content)
            metadata = post.metadata
            content_text = post.content
        except:
            # 如果没有frontmatter，使用文件名作为标题
            metadata = {}
            content_text = content
        
        # 获取文件修改时间作为发布日期
        mtime = os.path.getmtime(file_path)
        pub_date = datetime.fromtimestamp(mtime, tz=pytz.UTC)
        
        # 从文件路径生成URL
        relative_path = str(file_path.relative_to('docs'))
        url_path = relative_path.replace('\\', '/').replace('.md', '')
        
        # 如果没有标题，使用文件名（去掉.md后缀）
        title = metadata.get('title', '')
        if not title:
            title = file_path.stem
            
        # 获取描述，如果没有则使用内容的前200个字符
        description = metadata.get('description', '')
        if not description:
            # 移除markdown标记，获取纯文本
            clean_content = re.sub(r'[#*`_~]', '', content_text)
            description = clean_content.strip()[:200]
        
        return {
            'title': title,
            'description': description,
            'pub_date': pub_date,
            'url': f'https://mydocs.vercel.app/{url_path}',
            'content': content_text
        }

def generate_rss():
    """生成RSS文件"""
    # RSS头部
    rss_header = '''<?xml version="1.0" encoding="UTF-8" ?>
<rss version="2.0">
<channel>
    <title>MyDocs</title>
    <link>https://mydocs.vercel.app</link>
    <description>个人文档和笔记</description>
    <language>zh-cn</language>
    <lastBuildDate>{}</lastBuildDate>
'''.format(datetime.now(pytz.UTC).strftime('%a, %d %b %Y %H:%M:%S %z'))

    # RSS条目
    rss_items = []
    
    # 遍历所有markdown文件
    for root, _, files in os.walk('docs'):
        for file in files:
            if file.endswith('.md'):
                file_path = Path(root) / file
                try:
                    metadata = get_file_metadata(file_path)
                    item = f'''
    <item>
        <title>{metadata['title']}</title>
        <link>{metadata['url']}</link>
        <description><![CDATA[{metadata['description']}...]]></description>
        <pubDate>{metadata['pub_date'].strftime('%a, %d %b %Y %H:%M:%S %z')}</pubDate>
        <guid>{metadata['url']}</guid>
    </item>'''
                    rss_items.append(item)
                except Exception as e:
                    print(f"Error processing {file_path}: {e}")

    # RSS尾部
    rss_footer = '''
</channel>
</rss>'''

    # 组合完整的RSS内容
    rss_content = rss_header + ''.join(rss_items) + rss_footer

    # 写入文件
    with open('static/rss.xml', 'w', encoding='utf-8') as f:
        f.write(rss_content)

if __name__ == '__main__':
    generate_rss()