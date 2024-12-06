<template>
    <div>
    <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
      <div class="code-block">
        <h3>java源码</h3>
        <pre><code class="java">{{ javaCode }}</code></pre>
      </div>
      <div class="code-block">
        <h3>使用参数方式访问</h3>
        <pre><code class="java">{{ fixedJavaCode }}</code></pre>
      </div>
    </el-drawer>

    <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
        <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        <h2>下列三张图片通过不同的接口访问图片</h2>
    </div> 
        <div v-for="(image, index) in images" :key="index">
            <img :src="image.src" :alt="image.alt" style="max-width: 300px; height: auto;" />
            <p :style="{ color: 'black', textAlign: 'left' }">{{ image.description }}</p>
        </div>
    </div>
    
</template>

<script>
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
    data() {
        return {
            images: [],
            drawerVisible: false,
            javaCode: `
    @GetMapping("/GetImage")
    public ResponseEntity<byte[]> GetImage(@RequestParam("Image") String image) {
        String[] http = {"http://","https://"};
        //判断是否是网页请求
        if (!WebSecurity.RFS(http,image)){
            image = URL + "api/files/" + image;
        }
        return ssrfService.fetchImage(image);
    }
    
    public ResponseEntity<byte[]> fetchImage(String image){
        try {

            URL url = new URL(image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer))!= -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();

                byte[] imageData = outputStream.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // 根据实际图片类型设置

                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
      `,
      fixedJavaCode: `
    @GetMapping("/GetImage_No")
    public ResponseEntity<byte[]> GetImage_No(@RequestParam("Image") int num) {
        String image404 = URL + "api/files/404.png";
        //通过数据库获取图片路径
        SSRF_No SSRF = ssrfService.SSRFNO(num);
        if(SSRF != null) {
            return ssrfService.fetchImage(URL + SSRF.getPath());
        }else {
            return ssrfService.fetchImage(image404);
        }
        
       @Select("select * FROM imagepath where imagenumber = #{id}")
       SSRF_No SSRF_No(int id); 

    配合数据库使用参数化去访问图片
    URL已经写死访问的地址，这样就无法造成SSRF漏洞
      `,
    };
  },
    mounted() {
        const imageUrls = [
            '/api/files/2.jpg ',
            '/api/SSRF/GetImage?Image=2.jpg ',
            '/api/SSRF/GetImage_No?Image=2 '
        ];

        const descriptions = [
            '通过网站路径访问图片 /api/files/2.jpg',
            '存在SSRF漏洞的方式去访问图片 /api/SSRF/GetImage?Image=2.jpg',
            '使用参数方式去访问图片 /api/SSRF/GetImage_No?Image=2'
        ];

        const token = localStorage.getItem('token');
        console.log('即将发送请求，获取到的token：', token);
        imageUrls.forEach((url, index) => {
            this.$axios.get(url, {
                
            })
         .then(() => {
                    this.images.push({
                        src: url,
                        alt: `图片${index + 1}`,
                        description: descriptions[index]
                    });
                })
         .catch((error) => {
                    console.error(`获取图片${index + 1}时出错：`, error);
                });
        });
    },
    methods: {
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

img {
    max-width: 100%;
    height: auto;
}
p {
    text-align: center;
    margin-top: 5px;
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