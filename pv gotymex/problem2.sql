SELECT bc.*
FROM bank_config bc 
LEFT JOIN (
    SELECT 
       name, 
       MAX(revision) AS version
    FROM bank_config
    WHERE enable = 1 
    GROUP BY name
) cv
ON bc.name = cv.name 
AND bc.revision = cv.version
WHERE bc.enable = 1 
ORDER BY bc.id ASC;

SELECT ranked.* 
FROM (
    SELECT 
        bc.*,
        ROW_NUMBER() OVER ( 
            PARTITION BY name
            ORDER BY revision DESC, id DESC
        ) AS rn 
    FROM bank_config bc
    WHERE enable = 1
) ranked
WHERE rn = 1 
ORDER BY id ASC;
