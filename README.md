  <h1>Guia de Implantação do Aplicativo (Backend)</h1>
    <ol>
        <li>
            <p>
                Se escolher por clonar o projeto, vá até o diretório <code>pontoapp</code> e execute o comando:
            </p>
            <pre><code>git clone git@github.com:jpcchaves/project-insight.git (via SSH) ou https://github.com/jpcchaves/project-insight.git(via HTTPS)</code></pre>
            <pre><code>cd project-insight/pontoapp</code></pre>
            <pre><code>mvn clean package</code></pre>
        </li>
        <li>
            <p>
                Copie o arquivo <code>pontoapp.war</code> gerado na pasta target em <code>pontoapp/target</code> para a pasta <code>webapps</code> dentro do diretório raiz do Apache Tomcat (certifique-se de estar usando a versão 10.1.18, que foi a mesma utilizada no desenvolvimento do projeto).
            </p>
        </li>
        <li>
            <p>
                Inicie o Apache Tomcat. Navegue até o diretório do Tomcat e execute:
            </p>
            <pre><code>./bin/startup.sh</code></pre>
        </li>
        <li>
            <p>
                Verifique se o backend iniciou na porta 8080. Você pode acessar o aplicativo pelo navegador utilizando a seguinte URL:
            </p>
            <p>
                <a href="http://localhost:8080/pontoapp/" target="_blank">http://localhost:8080/pontoapp/</a>
            </p>
            <p>
               Se estiver funcionando, aparecera uma pagina com o error 404 do Tomcat.
            </p>
        </li>
    </ol>
