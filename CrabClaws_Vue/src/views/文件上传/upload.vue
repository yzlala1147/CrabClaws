<template>
  <div>
    <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
      任意文件上传
    <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
    </div>
    <input type="file" ref="fileInput" />
    <button @click="uploadFile">上传文件</button>
    <p v-if="uploadResult">{{ uploadResult }}</p>

      <el-drawer :visible.sync="drawerVisible" direction="rtl" size="70%" title="Java代码示例">
        <div class="code-block">
          <h3>java源码</h3>
          <pre><code class="java">{{ javaCode }}</code></pre>
        </div>
      </el-drawer>
  </div>
</template>

<script>
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
  name: 'FileUpload',
  data() {
    return {
      uploadResult: null,
      drawerVisible: false,
      javaCode: `
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        String filepath = uploadService.uploadservice(file);
        return Result.success(filepath);
    }
    
    public String uploadservice(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                // 创建上传文件保存目录（如果不存在）
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                // 获取原始文件名
                String originalFilename = file.getOriginalFilename();
                // 构建保存文件的路径
                Path destinationPath = Paths.get(uploadDir.getAbsolutePath(), originalFilename);
                // 将文件保存到指定路径
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                return "上传成功,文件路径保存在：/files/" + originalFilename;
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败";
            }
        } else {
            return "上传失败";
        }
    }
      `,
    };
  },
  methods: {
    uploadFile() {
      const file = this.$refs.fileInput.files[0];
      if (file) {
        const formData = new FormData();
        formData.append('file', file);

        this.$axios.post('api/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
      .then((response) => {
              this.uploadResult = response.data;
            })
      .catch((error) => {
              console.error('文件上传失败', error);
              this.uploadResult = '文件上传失败：' + error.message;
            });
      } else {
        this.uploadResult = '请选择要上传的文件';
      }
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

<style>
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