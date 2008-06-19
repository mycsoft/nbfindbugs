<?xml version="1.0" encoding="GBK"?>

<!--
    Document   : findbugs_csv.xsl
    Created on : 2008年5月16日, 下午4:55
    Author     : Ma Yichao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="GBK" />
    
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        
        <xsl:processing-instruction name="mso-application" >
            progid="Excel.Sheet"
        </xsl:processing-instruction>    
        
        
        
        <Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
                  xmlns:o="urn:schemas-microsoft-com:office:office"
                  xmlns:x="urn:schemas-microsoft-com:office:excel"
                  xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
                  xmlns:html="http://www.w3.org/TR/REC-html40">
            <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
                <LastAuthor>马翼超</LastAuthor>
                <Version>12.00</Version>
            </DocumentProperties>
            <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
            </ExcelWorkbook>
            <Styles>
                <Style ss:ID="Default" ss:Name="Normal">
                    <Alignment ss:Vertical="Center"/>
                    <Borders/>
                    <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
                    <Interior/>
                    <NumberFormat/>
                    <Protection/>
                </Style>
            </Styles>
            <Worksheet ss:Name="fb_results">
                <Table ss:ExpandedColumnCount="11"  x:FullColumns="1"
                       x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
                    <Column ss:Index="10" ss:AutoFitWidth="0" ss:Width="186.75"/>
                    
                    <Row>
                        <Cell><Data ss:Type="String">错误级别</Data></Cell>
                        <Cell><Data ss:Type="String">问题说明</Data></Cell>
                        <Cell><Data ss:Type="String">类名</Data></Cell>
                        <Cell><Data ss:Type="String">起始行</Data></Cell>
                        <Cell><Data ss:Type="String">终止行</Data></Cell>
                        <Cell><Data ss:Type="String">修改人</Data></Cell>
                        <Cell><Data ss:Type="String">修改日期</Data></Cell>
                        <Cell><Data ss:Type="String">检查人</Data></Cell>
                        <Cell><Data ss:Type="String">检查日期</Data></Cell>
                        <Cell><Data ss:Type="String">状态</Data></Cell>
                    </Row>
                    <xsl:for-each select="BugCollection">
                        <xsl:variable name="checktime" select="FindBugsSummary/@timestamp" />
                        <xsl:for-each select="BugInstance">
                            <xsl:variable name="type" select="./@type" />
                            <xsl:variable name="level" select="./@priority" />
                            <!--xsl:if test="$level > 0"-->
                            <xsl:for-each select=".//SourceLine">
                                
                                <Row>
                                    <Cell><Data ss:Type="Number"><xsl:value-of select="$level"/></Data></Cell>
                                    <Cell><Data ss:Type="String"><xsl:value-of select="$type"/></Data></Cell>
                                    <Cell><Data ss:Type="String"><xsl:value-of select="./@classname"/></Data></Cell>
                                    <Cell><Data ss:Type="Number"><xsl:value-of select="./@start"/></Data></Cell>
                                    <Cell><Data ss:Type="Number"><xsl:value-of select="./@end"/></Data></Cell>
                                    <Cell></Cell>
                                    <Cell></Cell>
                                    <Cell></Cell>
                                    <Cell><Data ss:Type="String"><xsl:value-of select="$checktime"/></Data></Cell>
                                    <Cell><Data ss:Type="String">未修改</Data></Cell>
                                    
                                </Row>
                            </xsl:for-each>
                            <!--/xsl:if-->
                        </xsl:for-each>
                    </xsl:for-each>
                    
                </Table>
                <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
                    <PageSetup>
                        <Header x:Margin="0.3"/>
                        <Footer x:Margin="0.3"/>
                        <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
                    </PageSetup>
                    <Selected/>
                    <Panes>
                        <Pane>
                            <Number>3</Number>
                            <ActiveRow>2</ActiveRow>
                            <ActiveCol>9</ActiveCol>
                        </Pane>
                    </Panes>
                    <ProtectObjects>False</ProtectObjects>
                    <ProtectScenarios>False</ProtectScenarios>
                </WorksheetOptions>
            </Worksheet>
        </Workbook>
        
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
