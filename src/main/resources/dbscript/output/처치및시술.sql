select K.pro_cd '수가코드',
K.pro_eng '영문명',
K.pro_kor '한글명',
K.icd_cd 'ICD-9-CM Code',
K.icd_name 'ICD-9-CM Name',
K.map_tp 'Mapping type',
K.pre_sct_id 'Precoordinated concept SCTID',
S.SCT_TERM2 'Precoordinated concept FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(S.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Precoordinated concept Hierarchy',
K.post_sct_id 'Postcoordinated Core concept SCTID',
P.SCT_TERM2 'Postcoordinated Core concept FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(P.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Postcoordinated Core concept Hierarchy',
ATT.ATT_SCT_ID 'Attribute SCTID',
ATM.SCT_TERM2 'Attribute FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(ATM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','') 'Attribute Hierarchy',
ATT.VAL_SCT_ID 'Value SCTID',
VTM.SCT_TERM2 'Value FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(VTM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','') 'Value Hierarchy',
case when ATT.ATT_SCT_ID is null then
concat(K.pre_sct_id,'|',S.SCT_TERM,'|')
else (
select concat(K.post_sct_id,'|', P.SCT_TERM,'|:(', GROUP_CONCAT(CONCAT(MA.ATT_SCT_ID,'|',A.SCT_TERM,' |= ',MA.VAL_SCT_ID,'|',V.SCT_TERM,'|') SEPARATOR ','),')')
    from EMR.temp_map_proc_att_val_20191213 MA
    inner join EMR.CM_SNOMEDCT A
    on MA.ATT_SCT_ID=A.SCT_ID
    inner join EMR.CM_SNOMEDCT V
    on MA.VAL_SCT_ID=V.SCT_ID
    where K.post_sct_id=MA.SCT_ID
and K.pro_cd=MA.ORI_CD
GROUP BY K.post_sct_id
    )
end 'SNOMED CT Expression'
from EMR.temp_cm_proc_20191216 K
left outer join EMR.CM_SNOMEDCT S
on S.SCT_ID = K.pre_sct_id
left outer join EMR.CM_SNOMEDCT P
on P.SCT_ID = K.post_sct_id
left outer join EMR.temp_map_proc_att_val_20191213 ATT
on K.pro_cd = ATT.ORI_CD
and K.post_sct_id=ATT.SCT_ID
left outer join EMR.CM_SNOMEDCT ATM
on ATT.ATT_SCT_ID=ATM.SCT_ID
left outer join EMR.CM_SNOMEDCT VTM
on ATT.VAL_SCT_ID=VTM.SCT_ID;