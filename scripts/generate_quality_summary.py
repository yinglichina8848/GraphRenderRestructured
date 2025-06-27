import xml.etree.ElementTree as ET
import os

# 路径配置：请根据你的项目结构调整
REPORT_DIR = "./target"
CHECKSTYLE_REPORT = os.path.join(REPORT_DIR, "checkstyle-result.xml")
PMD_REPORT = os.path.join(REPORT_DIR, "pmd.xml")
SPOTBUGS_REPORT = os.path.join(REPORT_DIR, "spotbugsXml.xml")
DEP_CHECK_REPORT = os.path.join(REPORT_DIR, "dependency-check-report.xml")
JACOCO_REPORT = os.path.join(REPORT_DIR, "jacoco/jacoco.xml")
OUTPUT_MD = "./doc/quality-summary.md"

def parse_checkstyle(path):
    if not os.path.exists(path):
        return None, "Report not found"
    tree = ET.parse(path)
    root = tree.getroot()
    errors = 0
    for file in root.findall("file"):
        errors += int(file.get("errors", 0))
    status = "✅ 通过" if errors == 0 else f"❌ {errors} 个错误"
    return errors, status

def parse_pmd(path):
    if not os.path.exists(path):
        return None, "Report not found"
    tree = ET.parse(path)
    root = tree.getroot()
    violations = 0
    for file in root.findall("file"):
        violations += len(file.findall("violation"))
    status = "✅ 通过" if violations == 0 else f"❌ {violations} 个违规"
    return violations, status

def parse_spotbugs(path):
    if not os.path.exists(path):
        return None, "Report not found"
    tree = ET.parse(path)
    root = tree.getroot()
    bugs = int(root.get("bugs", 0))
    status = "✅ 通过" if bugs == 0 else f"❌ {bugs} 个缺陷"
    return bugs, status

def parse_dependency_check(path):
    if not os.path.exists(path):
        return None, "Report not found"
    tree = ET.parse(path)
    root = tree.getroot()
    vulns = 0
    for vuln in root.findall(".//vulnerability"):
        vulns += 1
    status = "✅ 通过" if vulns == 0 else f"❌ {vulns} 个漏洞"
    return vulns, status

def parse_jacoco(path):
    if not os.path.exists(path):
        return None, "Report not found"
    tree = ET.parse(path)
    root = tree.getroot()
    counters = {}
    for counter in root.findall("counter"):
        ctype = counter.get("type")
        missed = int(counter.get("missed"))
        covered = int(counter.get("covered"))
        total = missed + covered
        coverage = (covered / total * 100) if total > 0 else 0
        counters[ctype] = coverage
    # 取常用指标，Class、Method、Line
    class_cov = counters.get("CLASS", 0)
    method_cov = counters.get("METHOD", 0)
    line_cov = counters.get("LINE", 0)
    status = "✅ 通过" if line_cov >= 80 else f"⚠️ 覆盖率较低 ({line_cov:.1f}%)"
    return {"Class": class_cov, "Method": method_cov, "Line": line_cov}, status

def write_summary(results):
    with open(OUTPUT_MD, "w", encoding="utf-8") as f:
        f.write("# 📊 代码质量摘要报告\n\n")
        f.write(f"> 项目：GraphRenderSystem\n\n---\n\n")
        f.write("## ✅ 自动化质量检查工具集成情况\n\n")
        f.write("| 工具         | 执行状态    | 详细数据 |\n")
        f.write("|--------------|-------------|----------|\n")
        f.write(f"| Checkstyle   | {results['checkstyle'][1]} | 错误数: {results['checkstyle'][0]} |\n")
        f.write(f"| PMD          | {results['pmd'][1]} | 违规数: {results['pmd'][0]} |\n")
        f.write(f"| SpotBugs     | {results['spotbugs'][1]} | 缺陷数: {results['spotbugs'][0]} |\n")
        f.write(f"| OWASP DC     | {results['dependency_check'][1]} | 漏洞数: {results['dependency_check'][0]} |\n")
        f.write(f"| JaCoCo       | {results['jacoco'][1]} | 覆盖率: Class {results['jacoco'][0]['Class']:.1f}%, Method {results['jacoco'][0]['Method']:.1f}%, Line {results['jacoco'][0]['Line']:.1f}% |\n")
        f.write("\n---\n")

def main():
    results = {}
    results["checkstyle"] = parse_checkstyle(CHECKSTYLE_REPORT)
    results["pmd"] = parse_pmd(PMD_REPORT)
    results["spotbugs"] = parse_spotbugs(SPOTBUGS_REPORT)
    results["dependency_check"] = parse_dependency_check(DEP_CHECK_REPORT)
    results["jacoco"] = parse_jacoco(JACOCO_REPORT)
    write_summary(results)
    print(f"✅ 质量摘要报告生成完毕：{OUTPUT_MD}")

if __name__ == "__main__":
    main()
