<template>
    <div class="CMD1">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            命令执行_exec
        <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 200%">
                    <el-table-column prop="data" label="data" width="800">
                        <template slot-scope="scope">
                            <div v-html="parseText(scope.row.data)"></div>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="cmd=" style="width: 120px;"></el-input>
                    <el-button @click="fetchData" type="primary">查询</el-button>
                </div> 
            </el-main>
        </el-container>
        <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
          <div class="code-block">
            <h3>java源码</h3>
            <pre><code class="java">{{ javaCode }}</code></pre>
          </div>
        </el-drawer>
    </div>
</template>

<script>
import qs from 'qs'; // 引入qs库，用于处理表单数据格式转换
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
data() {
    return {
      tableData: [],
      inputValue: '',
      drawerVisible: false,
      javaCode: `
    @PostMapping("/cmd1")
    public Result execCommand(@RequestParam("cmd") String cmd) {
        return cmdService.execCommand(cmd);
    }

    
    public Result execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c" + command);
            // 获取命令执行的输出流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));

            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\\n");
            }

            // 等待命令执行完成并获取退出码
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return Result.success(output.toString());
            } else {
                return Result.error("命令执行错误" + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.error("命令执行错误");
    }
      `,
    };
  },
    methods: {
        fetchData() {
            const cmd = this.inputValue;
            this.$axios.post('api/cmd/cmd1', qs.stringify({ cmd: cmd }), {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            })
            .then((result) => {
                this.tableData = [result.data];
                this.formattedTableData = this.tableData.map(item => ({
                   ...item,
                    data: this.parseText(item.msg)
                }));
            })
          .catch((error) => {
                console.error('获取数据出错：', error);
            });
        },
    parseText(text) {
            return text? text.replace(/\n/g, '<br>') : '';
        },
        highlightCode() {
      this.$nextTick(() => {
        const codeBlocks = document.querySelectorAll('pre code');
        codeBlocks.forEach((block) => {
          hljs.highlightBlock(block);
        });
      });
    }
  },
  watch: {
    drawerVisible(newVal) {
      if (newVal) {
        this.highlightCode(); // 当抽屉打开时进行高亮显示
      }
    }
  }
};
</script>

<style scoped>
.CMD1 {
    text-align: center;
}

.main-container {
    padding: 20px;
    background-color: #ffffff;
}

.input-container {
    margin-top: 20px;
}
.input-button-container {
    display: flex;
    align-items: center;
    margin-top: 20px;
}
.code-block {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f4f4f4;
  border-radius: 8px;
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.ct-button {
  width: 120px;
  height: 40px;
  float: right;
}

.code-block {
  margin-left: 0;
  color: black;
  font-size: 16px;
  text-align: left;
}
</style>