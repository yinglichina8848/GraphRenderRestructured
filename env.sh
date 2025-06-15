# 设置环境变量（根据实际端口调整）
export OPENAI_API_BASE=http://192.168.0.252:9998/v1
export OPENAI_API_KEY=sk-no-key-required
export OPENAI_MODEL=openai/DeepSeek-R1-0528-Qwen3-8B
aider --model openai/DeepSeek-R1-0528-Qwen3-8B --temperature 0.1  --context-length 16384 
