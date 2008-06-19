<?xml version="1.0" encoding="GBK"?>

<!--
    Document   : findbugs_csv.xsl
    Created on : 2008年5月16日, 下午4:55
    Author     : Ma Yichao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text" encoding="GBK"/>
    
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:for-each select="BugCollection">
            <xsl:variable name="checktime" select="FindBugsSummary/@timestamp" />
错误级别,问题说明,类名,起始行,终止行,修改人,修改日期,检查人,检查日期,状态
            <xsl:for-each select="BugInstance">
                <xsl:variable name="type" select="./@type" />
                <xsl:variable name="level" select="./@priority" />
                <xsl:for-each select=".//SourceLine">
<xsl:value-of select="$level"/>,<xsl:value-of select="$type"/>,<xsl:value-of select="./@classname"/>,<xsl:value-of select="./@start"/>,<xsl:value-of select="./@end"/>,,,,<xsl:value-of select="$checktime"/>,未修改
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
    <!--
    <xsl:template match="BugInstance">
        <xsl:variable name="type" select="./@type" />
        <xsl:variable name="level" select="./@priority" />
        <xsl:for-each select=".//SourceLine">
            <xsl:value-of select="$level"/>,<xsl:value-of select="$type"/>,<xsl:value-of select="./@classname"/>,<xsl:value-of select="./@start"/>,<xsl:value-of select="./@end"/>,,,,<xsl:value-of select="checktime"/>,状态
        </xsl:for-each>
        
    </xsl:template>
    <xsl:template match="SourceLine">
        <xsl:apply-templates />
        错误类型:<xsl:value-of select="./@type"/>,
    </xsl:template>
    -->
</xsl:stylesheet>
