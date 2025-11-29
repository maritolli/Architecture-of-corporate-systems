<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8"/>

  <xsl:template match="/">
    <html>
      <head>
        <meta charset="UTF-8"/>
        <title>Библиотека — XML View</title>
        <style>
          body { font-family: Arial, sans-serif; margin: 20px; }
          table { border-collapse: collapse; width: 100%; margin-top: 10px; }
          th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
          th { background-color: #f2f2f2; }
          .section { margin: 20px 0; }
        </style>
      </head>
      <body>
        <h1>📚 Данные из библиотеки</h1>

        <xsl:choose>

           <!-- Если это выдачи -->
          <xsl:when test="count(//borrowingId) > 0">
            <div class="section">
              <h2>Выдачи</h2>
              <table>
                <tr><th>ID</th><th>Книга</th><th>Читатель</th><th>Дата</th></tr>
                <xsl:for-each select="//item[borrowingId]">
                  <tr>
                    <td><xsl:value-of select="borrowingId"/></td>
                    <td>
                      <xsl:choose>
                        <xsl:when test="book/title"><xsl:value-of select="book/title"/></xsl:when>
                        <xsl:otherwise>—</xsl:otherwise>
                      </xsl:choose>
                    </td>
                    <td>
                      <xsl:choose>
                        <xsl:when test="reader/name"><xsl:value-of select="reader/name"/></xsl:when>
                        <xsl:otherwise>—</xsl:otherwise>
                      </xsl:choose>
                    </td>
                    <td><xsl:value-of select="borrowDate"/></td>
                  </tr>
                </xsl:for-each>
              </table>
            </div>
          </xsl:when>
          
          <!-- Если это книги -->
          <xsl:when test="count(//bookId) > 0">
            <div class="section">
              <h2>Книги</h2>
              <table>
                <tr><th>ID</th><th>Название</th><th>Автор</th><th>Статус</th></tr>
                <xsl:for-each select="//item[bookId]">
                  <tr>
                    <td><xsl:value-of select="bookId"/></td>
                    <td><xsl:value-of select="title"/></td>
                    <td>
                      <xsl:choose>
                        <xsl:when test="author/name"><xsl:value-of select="author/name"/></xsl:when>
                        <xsl:otherwise>—</xsl:otherwise>
                      </xsl:choose>
                    </td>
                    <td><xsl:value-of select="status"/></td>
                  </tr>
                </xsl:for-each>
              </table>
            </div>
          </xsl:when>

          <!-- Если это авторы -->
          <xsl:when test="count(//authorId) > 0">
            <div class="section">
              <h2>Авторы</h2>
              <table>
                <tr><th>ID</th><th>Имя</th><th>Страна</th></tr>
                <xsl:for-each select="//item[authorId]">
                  <tr>
                    <td><xsl:value-of select="authorId"/></td>
                    <td><xsl:value-of select="name"/></td>
                    <td><xsl:value-of select="country"/></td>
                  </tr>
                </xsl:for-each>
              </table>
            </div>
          </xsl:when>

         

          <xsl:otherwise>
            <p>Неизвестный формат данных.</p>
          </xsl:otherwise>
        </xsl:choose>

        <p>
          <a href="/api/authors">Авторы</a> |
          <a href="/api/books" target="_blank">Книги</a> |
          <a href="/api/borrowings">Выдачи</a> |
          <a href="/">Главная</a>
        </p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>