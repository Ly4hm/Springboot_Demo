package top.lyahm.readlist.utils;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;

import java.util.Arrays;

public class GPTUtil {
    private final ChatGPT gpt;


    /**
     * 初始化 GPT
     */
    public GPTUtil() {
        this.gpt = ChatGPT.builder()
                .apiKey("sk-nFQ3oQQkMS32P2Jr4333885bDe3e4e53872b850303A88f76")
                .apiHost("https://openkey.cloud/") //反向代理地址
                .timeout(3000)
                .build()
                .init();
    }

    /**
     * 对给定的温度、湿度和光照进行分析，并返回分析结果。
     *
     * @param temp  温度值
     * @param humi  湿度值
     * @param light 光照值
     * @return 分析结果字符串
     */
    public String getAnalyse(float temp, float humi, float light) {
        Message system = Message.ofSystem(
                "你现在是一个智能管家，根据我给出的相关温度，湿度，环境亮度的数据，" +
                "给出我相关建议，比如光照太弱要开灯，温度太低或者太高应该通过空调来调节，" +
                "湿度应该通过加湿器来条件，相关建议写成一段不超过100字的话，以管家的语气来给出我建议，" +
                "仅仅告知我即可，后面的事情不需要管家来处理"
        );
        Message message = Message.of("室内当前温度为%s,湿度为%s,光照为%s".formatted(temp, humi, light));

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.3) // 数值越高创造性越高
                .build();
        ChatCompletionResponse response = this.gpt.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }
}
