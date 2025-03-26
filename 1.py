import os

# 定义文件夹路径
folder_path = r'f:\个人文件\博客\docusaurus\mydocs\docs\左耳听风'

# 读取文件夹中的文件
files = sorted(os.listdir(folder_path))

# 遍历文件夹中的文件并重命名
for i, filename in enumerate(files):
    if filename.endswith('.md'):
        # 分离文件名和扩展名
        name_without_extension = filename[:-3]
        
        # 分离编号和标题
        parts = name_without_extension.split('-')
        if len(parts) > 1:
            number = parts[0]
            title = '-'.join(parts[1:])  # 将剩余部分用连字符重新组合
            
            # 创建新的文件名
            new_filename = f"{title}-{number}.md"
        
            # 构造旧文件和新文件的完整路径
            old_file_path = os.path.join(folder_path, filename)
            new_file_path = os.path.join(folder_path, new_filename)

            # 重命名文件
            os.rename(old_file_path, new_file_path)

print("文件名修改完成！")
