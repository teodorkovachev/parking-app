CREATE UNIQUE NONCLUSTERED INDEX idx_vehicleid_notnull ON dbo.parking_spot(vehicle_id) WHERE vehicle_id IS NOT NULL;