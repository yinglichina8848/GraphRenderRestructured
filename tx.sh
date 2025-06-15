import openai

openai.api_key = "dummy"
openai.api_base = "http://192.168.0.252:9998/v1"

response = openai.ChatCompletion.create(
    model="DeepSeek-R1-0528-Qwen3-8B",
    messages=[
        {"role": "user", "content": "你好，请问你是谁？"}
    ]
)

print(response.choices[0].message['content'])

