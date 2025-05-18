import os
import re
from datetime import datetime
from pathlib import Path
import frontmatter
import pytz

def get_file_metadata(file_path):
    """解析markdown文件的元数据"""
    with open(file_path, 'r', encoding='utf-8') as f:
        post = frontmatter.load(f)
        metadata = post.metadata
        
        # 获取文件修改时间作为发布日期
        mtime = os.path.getmtime(file_path)
        pub_date = datetime.fromtimestamp(mtime, tz=pytz.UTC)
        
        # 从文件路径生成URL
        relative_path = str(file_path.relative_to('docs'))
        url_path = relative_path.replace('\\', '/').replace('.md', '')
        
        return {
            'title': metadata.get('title', ''),
            'description': metadata.get('description', ''),
            'pub_date': pub_date,
            'url': f'https://doc.minddiy.top/{url_path}',
            'content': post.content
        }

def generate_rss():
    """生成RSS文件"""
    # RSS头部
    rss_header = '''<?xml version="1.0" encoding="UTF-8" ?>
<rss version="2.0">
<channel>
    <title>MyDocs</title>
    <link>https://doc.minddiy.top</link>
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
                    if metadata['title']:  # 只包含有标题的文章
                        item = f'''
    <item>
        <title>{metadata['title']}</title>
        <link>{metadata['url']}</link>
        <description><![CDATA[{metadata['description'] or metadata['content'][:200]}...]]></description>
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