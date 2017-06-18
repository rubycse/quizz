<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>

    <!--<xsl:include href="common/header.xsl"/>-->
    <!--<xsl:include href="common/footer.xsl"/>-->
    <!--<xsl:include href="2016_6_0/_serviceIntervention.xsl"/>-->

    <xsl:template match="/supportPlan">

        <!--<xsl:variable name="xpdf" select="archiveUtil:getParameterFromRequest('xpdf')"/>-->

        <html>
            <head>
                <title>Quiz Template :: PDF</title>
                <link href="/ma/css/bootstrap-xsl-2016.6.0.css" rel="stylesheet" type="text/css"/>
                <style>

                    <!--<xsl:if test="(not(string($xpdf)) or $xpdf = 'false')">-->
                        <!--.header {-->
                        <!--font-size : 15px;-->
                        <!--font-weight : bold;-->
                        <!--margin-top : 20px;-->
                        <!--}-->

                        <!--body {-->
                        <!--font-size : 13px;-->
                        <!--}-->

                        <!--.table {-->
                        <!--width: 90%;-->
                        <!--margin-left : 0px;-->
                        <!--}-->

                        <!--table.archive-data td.text-left {-->
                        <!--text-align: left;-->
                        <!--}-->

                        <!--table.archive-data {-->
                        <!--margin-left: 0px;-->
                        <!--}-->

                        <!--table.archive-data td.small-padding {-->
                        <!--padding: 0 20px 5px 5px;-->
                        <!--}-->

                        <!--table.archive-data td.width-50 {-->
                        <!--width: 50% !important;-->
                        <!--}-->

                        <!--table.archive-data td.width-75 {-->
                        <!--width: 75% !important;-->
                        <!--}-->
                        <!--.archive-data td span {-->
                        <!--font-size : 12px;-->
                        <!--}-->
                        <!--.sub-section .header span {-->
                        <!--font-size : 15px;-->
                        <!--}-->
                    <!--</xsl:if>-->
                </style>
            </head>
            <body>
                <div class="form-header">
                    <xsl:value-of select="Quiz"/>
                </div>

                <!--<xsl:call-template name="formInfo"/>-->

                <div class="panel">
                    <div class="panel-heading">
                        <xsl:value-of select="Quiz"/>
                    </div>

                    <div class="sub-section">
                        <div class="header">
                            <span class="xpdf-underline">
                                <xsl:value-of select="name"/>
                            </span>
                        </div>

                    </div>

                </div>

                <!--<xsl:for-each select="serviceInterventions/serviceIntervention">-->
                    <!--<div class="panel">-->
                        <!--<div class="panel-heading">-->
                            <!--<xsl:value-of select="archiveUtil:getMessage('label.cmp.plan.service.intervention')"/>-->
                            <!--<xsl:variable name="index" select="position()"/>-->
                            <!--#<xsl:value-of select="$index"/>-->
                        <!--</div>-->

                        <!--<xsl:call-template name="interventionForm" />-->
                    <!--</div>-->
                <!--</xsl:for-each>-->

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>