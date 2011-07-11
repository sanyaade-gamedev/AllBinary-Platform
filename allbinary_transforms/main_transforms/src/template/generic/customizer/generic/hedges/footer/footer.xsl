<?xml version="1.0" encoding="UTF-8" ?>

<!--
AllBinary Open License Version 1
Copyright (c) 2011 AllBinary

By agreeing to this license you and any business entity you represent are
legally bound to the AllBinary Open License Version 1 legal agreement.

You may obtain the AllBinary Open License Version 1 legal agreement from
AllBinary or the root directory of AllBinary's AllBinary Platform repository.

Created By: Travis Berthelot
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" />

    <xsl:template match="/html" >
      <xsl:for-each select="en" >
         <xsl:for-each select="US" >
            <xsl:for-each select="FOOTER_NAME" >
            <xsl:variable name="footerText" select="FOOTER_TEXT" />

<table class="table" >

  <tr>
    <td width="51%">Copyright Text:</td>
    <td width="49%"><input class="textField" type="text" name="%= FooterData.TEXT %" size="30" value="{$footerText}" /><br />
    </td>
  </tr>
    
</table>

            </xsl:for-each>            
         </xsl:for-each>
       </xsl:for-each>                     
    </xsl:template>

</xsl:stylesheet> 