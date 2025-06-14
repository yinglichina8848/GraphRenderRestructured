import os
import time

AUTHOR = "liying"  # 修改为你自己的名字
PROJECT_DIR = "/home/liying/IdeaProjects/GraphRenderRestructured/src"  # 修改为你的 Java 源码目录

def add_javadoc(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    # 如果已经包含 @author 或者没有类定义，跳过
    if any('@author' in line for line in lines) or not any('public class' in line for line in lines):
        print(f"Skipped: {file_path}")
        return

    file_name = os.path.basename(file_path)
    class_name = file_name.replace(".java", "")
    package_line_index = next((i for i, line in enumerate(lines) if line.startswith("package")), None)

    if package_line_index is None:
        print(f"No package found in {file_path}, skipping.")
        return

    create_time = time.strftime('%Y-%m-%d', time.localtime(os.path.getctime(file_path)))
    modify_time = time.strftime('%Y-%m-%d', time.localtime(os.path.getmtime(file_path)))

    javadoc = [
        "/**\n",
        f" * {class_name}\n",
        " *\n",
        f" * @author {AUTHOR}\n",
        f" * @date {create_time}\n",
        f" * @lastModified {modify_time}\n",
        " */\n"
    ]

    new_lines = lines[:package_line_index] + javadoc + lines[package_line_index:]

    with open(file_path, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)

    print(f"Updated: {file_path}")

def process_directory(root_dir):
    for root, _, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                add_javadoc(os.path.join(root, file))

if __name__ == "__main__":
    process_directory(PROJECT_DIR)
