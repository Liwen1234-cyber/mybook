import os

def generate_sidebar_items(folder_path):
    # 获取文件夹下的所有文件和文件夹
    files = os.listdir(folder_path)
    # 过滤掉文件夹，只保留文件，并去掉文件扩展名
    items = [os.path.splitext(file)[0] for file in files if os.path.isfile(os.path.join(folder_path, file))]
    # 根据你的格式生成items列表
    formatted_items = [f"左耳听风/{item}" for item in items]
    return formatted_items

# 你的文件夹路径
folder_path = r'f:\个人文件\博客\docusaurus\mydocs\docs\左耳听风'
sidebar_items = generate_sidebar_items(folder_path)

# 打印结果
print(sidebar_items)
