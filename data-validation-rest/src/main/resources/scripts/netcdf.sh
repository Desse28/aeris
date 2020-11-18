#!/bin/bash

ncks --jsn -v WVT ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_WVT.json
ncks --jsn -v UWE ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_UWE.json
ncks --jsn -v UWE_Qflag ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_UWE_Qflag.json
ncks --jsn -v VSN ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_VSN.json
ncks --jsn -v VSN_Qflag ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_VSN_Qflag.json
ncks --jsn -v WVT_Qflag ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.nc > ./../netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_WVT_Qflag.json