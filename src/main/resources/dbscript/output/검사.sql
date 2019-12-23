#								


# kex_cd, grp1, grp2, kor, eng, eng2, Component, Property, Time, System, Scale, Method, LOINC_ID, LOINCNM, map_type, sct_id, sct_term, axis, comp
#'C1561', '나560주4가', '조직병리검사', '조직병리검사 [1장기당] -외부슬라이드 판독(Level A)', '', '', '', '', '', '', '', 'Histopathology test', '', '', 'exactly mapped', '252416005 ', 'Histopathology test', 'procedure', '1\r'

select 
M.kex_cd as '수가코드'
, M.kor as '한글명'
, M.eng as '영문명'
, M.Component as 'LOINC COMPONENT'
, M.Property as 'LOINC PROPERTY'
, M.Time as 'LOINC TIME'
, M.System as 'LOINC SYSTEM'
, M.Scale as 'LOINC SCALE'
, M.Method as 'LOINC METHOD'
, L.LOINC_ID as 'LOINC ID'
, L.Com_Name as 'LOINC Long Common Name'
, M.map_type as 'Mapping type'
,case when P.ATT_SCT_ID  is  null then  M.sct_id else '' end as 'Precoordinated concept SCTID'
,case when P.ATT_SCT_ID  is  null then M.sct_term else ''  end as 'Precoordinated concept FSN'
,case when P.ATT_SCT_ID  is  null then M.axis else '' end  as 'Precoordinated concept Hierarchy'
,case when P.ATT_SCT_ID  is NOT null then M.sct_id else '' end 'Postcoordinated Core concept SCTID'
,case when P.ATT_SCT_ID  is NOT null then M.sct_term else ''  end 'Postcoordinated Core concept FSN'
,case when P.ATT_SCT_ID  is NOT null then M.axis else '' end 'Postcoordinated Core concept Hierarchy'

,ATM.SCT_ID 'Attribute SCTID'
,ATM.SCT_TERM 'Attribute FSN'
,REGEXP_REPLACE( REGEXP_SUBSTR(ATM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Attribute Hierarchy'

,VTM.SCT_ID 'Value SCTID'
,VTM.SCT_TERM  'Value FSN'
,REGEXP_REPLACE( REGEXP_SUBSTR(VTM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Value Hierarchy'

,
case when P.ATT_SCT_ID is null  then 
	concat( M.SCT_ID,'|',M.SCT_TERM,'|')
else 
	(
    select concat( M.SCT_ID,'|',M.SCT_TERM,'|:(' ,GROUP_CONCAT(CONCAT(MA.ATT_SCT_ID,'|',A.SCT_TERM,' |= ', MA.VAL_SCT_ID,'|',V.SCT_TERM,'|') SEPARATOR ' , ' ),')')
	from EMR.rv_kex_post_codi_20191212 MA
	inner join EMR.CM_SNOMEDCT A
	on MA.ATT_SCT_ID=A.SCT_ID
	inner join EMR.CM_SNOMEDCT V
	on MA.VAL_SCT_ID=V.SCT_ID
 	where M.kex_cd=MA.kex_cd
	) 
end 
 as 'SNOMED CT Expression'
 from 
rv_cm_kexam_20191212 M
left outer join rv_edi_loinc_20191212 L
on M.kex_cd=L.kex_cd
left outer join rv_kex_post_codi_20191212 P
on M.kex_cd=P.kex_cd
left outer join EMR.CM_SNOMEDCT ATM
on P.ATT_SCT_ID=ATM.SCT_ID 
left outer join EMR.CM_SNOMEDCT VTM
on P.VAL_SCT_ID=VTM.SCT_ID 

#where M.kex_cd='D0327'


