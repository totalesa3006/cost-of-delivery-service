
INSERT INTO cost_of_delivery (priority, rule_name,min_weight,max_weight,min_volume,max_volume,size_factor,cost_factor) VALUES (1, 'REJECT',50.00,9999999999999.99,0.0,0.0,'WEIGHT',-1.0);

INSERT INTO cost_of_delivery (priority, rule_name,min_weight,max_weight,min_volume,max_volume,size_factor,cost_factor) VALUES (2, 'HEAVY_PARCEL',10.00,50.00,0.0,0.0,'WEIGHT',20.00);

INSERT INTO cost_of_delivery (priority, rule_name,min_weight,max_weight,min_volume,max_volume,size_factor,cost_factor) VALUES (3, 'SMALL_PARCEL',0.0,0.0,0.0,1500.0,'VOLUME',0.03);

INSERT INTO cost_of_delivery (priority, rule_name,min_weight,max_weight,min_volume,max_volume,size_factor,cost_factor) VALUES (4, 'MEDIUM_PARCEL',0.0,0.0,1500.0,2500.0,'VOLUME',0.04);

INSERT INTO cost_of_delivery (priority, rule_name,min_weight,max_weight,min_volume,max_volume,size_factor,cost_factor) VALUES (5, 'LARGE_PARCEL',0.0,0.0,2500.0,9999999999999.99,'VOLUME',0.05);