<?xml version="1.0" encoding="GBK"?>

<!--
    Document   : findbugs_csv.xsl
    Created on : 2008��5��16��, ����4:55
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
���󼶱�,����˵��,����,��ʼ��,��ֹ��,�޸���,�޸�����,�����,�������,״̬
            <xsl:for-each select="BugInstance">
                <xsl:variable name="type" select="./@type" />
                <xsl:variable name="level" select="./@priority" />
                <xsl:for-each select=".//SourceLine">
<xsl:value-of select="$level"/>,<xsl:value-of select="$type"/>,<xsl:value-of select="./@classname"/>,<xsl:value-of select="./@start"/>,<xsl:value-of select="./@end"/>,,,,<xsl:value-of select="$checktime"/>,δ�޸�
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
    <!--
    <xsl:template match="BugInstance">
        <xsl:variable name="type" select="./@type" />
        <xsl:variable name="level" select="./@priority" />
        <xsl:for-each select=".//SourceLine">
            <xsl:value-of select="$level"/>,<xsl:value-of select="$type"/>,<xsl:value-of select="./@classname"/>,<xsl:value-of select="./@start"/>,<xsl:value-of select="./@end"/>,,,,<xsl:value-of select="checktime"/>,״̬
        </xsl:for-each>
        
    </xsl:template>
    <xsl:template match="SourceLine">
        <xsl:apply-templates />
        ��������:<xsl:value-of select="./@type"/>,
    </xsl:template>
    -->
</xsl:stylesheet>
