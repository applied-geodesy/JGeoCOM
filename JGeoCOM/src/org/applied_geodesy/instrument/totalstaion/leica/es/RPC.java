/***********************************************************************
* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
*                                                                      *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 3 of the License, or    *
* at your option any later version.                                    *
*                                                                      *
* This program is distributed in the hope that it will be useful,      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
* GNU General Public License for more details.                         *
*                                                                      *
* You should have received a copy of the GNU General Public License    *
* along with this program; if not, see <http://www.gnu.org/licenses/>  *
* or write to the                                                      *
* Free Software Foundation, Inc.,                                      *
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
*                                                                      *
***********************************************************************/

package org.applied_geodesy.instrument.totalstaion.leica.es;

public enum RPC {
	// List of Remote Procedure Calls (RPC) 
	AUS_GetUserAtrState (18006),
	AUS_GetUserLockState (18008),
	AUS_SetUserAtrState (18005),
	AUS_SetUserLockState (18007),
	AUT_ChangeFace (9028),
	AUT_FineAdjust (9037),
	AUT_GetFineAdjustMode (9030),
	AUT_GetSearchArea (9042),
	AUT_GetUserSpiral (9040),
	AUT_LockIn (9013),
	AUT_MakePositioning (9027),
	AUT_PS_EnableRange (9048),
	AUT_PS_SearchNext (9051),
	AUT_PS_SearchWindow (9052),
	AUT_PS_SetRange (9047),
	AUT_ReadTimeout (9012),
	AUT_ReadTol (9008),
	AUT_Search (9029),
	AUT_SetFineAdjustMode (9031),
	AUT_SetSearchArea (9043),
	AUT_SetTimeout (9011),
	AUT_SetTol (9007),
	AUT_SetUserSpiral (9041),

	BAP_GetMeasPrg (17018),
	BAP_GetPrismDef (17023),
	BAP_GetPrismType (17009),
	BAP_GetPrismType2 (17031),
	BAP_GetTargetType (17022),
	BAP_GetUserPrismDef (17033),
	BAP_MeasDistanceAngle (17017),
	BAP_SearchTarget (17020),
	BAP_SetMeasPrg (17019),
	BAP_SetPrismType (17008),
	BAP_SetPrismType2 (17030),
	BAP_SetTargetType (17021),
	BAP_SetUserPrismDef (17032),
	BAP_GetATRSetting(17034),
	BAP_SetATRSetting(17035),
	BAP_GetRedATRFov(17036),
	BAP_SetRedATRFov(17037),
	BAP_GetATRPrecise(17039),
	BAP_SetATRPrecise(17040),
	
	BMM_BeepAlarm (11004),
	BMM_BeepNormal (11003),
	
	CAM_AF_ContinuousAutofocus (23669),
	CAM_AF_FocusContrastArroundCurrent (23663),
	CAM_AF_GetChipWindowSize (23668),
	CAM_AF_GetMotorPosition (23644),
	CAM_AF_PositFocusMotorToDist (23652),
	CAM_AF_PositFocusMotorToInfinity (23677),
	CAM_AF_SetMotorPosition (23645),
	CAM_AF_SingleShotAutofocus (23662),
	CAM_GetCameraFoV (23619),
	CAM_GetCameraPowerSwitch (23636),
	CAM_GetCamPos (23611),
	CAM_GetCamViewingDir (23613),
	CAM_GetZoomFactor (23609),
	CAM_IsCameraReady (23627),
	CAM_OAC_GetCrossHairPos (23671),
	CAM_OVC_GetActCameraCentre (23624),
	CAM_OVC_ReadInterOrient (23602),
	CAM_OVC_ReadExterOrient (23603),
	CAM_OVC_SetActDistance (23625),
	CAM_SetActualImageName (23622),
	CAM_SetCameraPowerSwitch (23637),
	CAM_SetCameraProperties (23633),
	CAM_SetWhiteBalanceMode (23626),
	CAM_SetZoomFactor (23608),
	CAM_StartRemoteVideo (23675),
	CAM_StopRemoteVideo (23676),
	CAM_TakeImage (23623),
	CAM_WaitForCameraReady (23638),

	COM_GetBinaryAvailable (113),
	COM_GetDoublePrecision (108),
	COM_GetSWVersion (110),
	COM_NullProc (0),
	COM_SetBinaryAvailable (114),
	COM_SetDoublePrecision (107),
	COM_SwitchOffTPS (112),
	COM_SwitchOnTPS (111),
	
	CSV_GetDateTime (5008),
	CSV_GetDateTimeCentiSec (5117),
	CSV_GetDeviceConfig (5035),
	CSV_GetInstrumentName (5004),
	CSV_GetInstrumentNo (5003),
	CSV_GetIntTemp (5011),
	CSV_GetReflectorlessClass (5100),
	CSV_GetSWVersion (5034),
	CSV_CheckPower (5039),
	CSV_SetDateTime (5007),
	CSV_GetCharging (5162),
	CSV_GetLaserlotIntens (5041),
	CSV_GetLaserlotStatus (5042),
	CSV_GetPreferredPowerSource (5164),
	CSV_GetStartUpMessageMode (5156),
	CSV_GetVoltage (5165),
	CSV_SetCharging (5161),
	CSV_SetLaserlotIntens (5040),
	CSV_SetPreferredPowerSource (5163),
	CSV_SetStartUpMessageMode (5155),
	CSV_SwitchLaserlot (5043),

	EDM_GetEglIntensity (1058),
	EDM_Laserpointer (1004),
	EDM_SetEglIntensity (1059),
	EDM_IsContMeasActive (1070),
	EDM_SetBoomerangFilter (1061),
	
	FTR_SetupList(23306),
	FTR_List(23307),
	FTR_AbortList(23308),
	TR_SetupDownload(23303),
	FTR_Download(23304),
	FTR_AbortDownload(23305),
	FTR_Delete(23309),
	FTR_DeleteDir(23315),
	FTR_SetupDownloadLarge(23313),
	FTR_DownloadXL(23314),
	
	IMG_GetTccConfig (23400),
	IMG_SetTccConfig (23401),
	IMG_TakeTccImage (23402),
	IMG_SetTccExposureTime (23403),
	
	IOS_BeepOff (20000),
	IOS_BeepOn (20001),
	
	KDM_SetLcdPower(23107),
	KDM_GetLcdPower(23108),

	MOT_ReadLockStatus (6021),
	MOT_SetVelocity (6004),
	MOT_StartController (6001),
	MOT_StopController (6002),

	SUP_GetConfig (14001),
	SUP_SetConfig (14002),
	SUP_SetPowerFailAutoRestart (14006),

	TMC_DoMeasure (2008),
	TMC_GetAngle1 (2003),
	TMC_GetAngle5 (2107),
	TMC_GetAngSwitch (2014),
	TMC_GetAtmCorr (2029),
	TMC_GetCoordinate (2082),
	TMC_GetEdmMode (2021),
	TMC_GetFace (2026),
	TMC_GetFullMeas (2167),
	TMC_GetHeight (2011),
	TMC_GetInclineSwitch (2007),
	TMC_GetPrismCorr (2023),
	TMC_GetRefractiveCorr (2031),
	TMC_GetRefractiveMethod (2091),
	TMC_GetAtmPpm (2151),
	TMC_GetGeoPpm (2154),
	TMC_GetSignal (2022),
	TMC_GetSimpleCoord (2116),
	TMC_GetSimpleMea (2108),
	TMC_GetSlopeDistCorr (2126),
	TMC_GetStation (2009),
	TMC_IfDataAzeCorrError (2114),
	TMC_IfDataIncCorrError (2115),
	TMC_QuickDist (2117),
	TMC_SetAngSwitch (2016),
	TMC_SetAtmCorr (2028),
	TMC_SetEdmMode (2020),
	TMC_SetHandDist (2019),
	TMC_SetHeight (2012),
	TMC_SetInclineSwitch (2006),
	TMC_SetPrismCorr (2024),
	TMC_SetOrientation (2113),
	TMC_SetRefractiveCorr (2030),
	TMC_SetRefractiveMethod (2090),
	TMC_SetAtmPpm (2148),
	TMC_SetGeoPpm(2153),
	TMC_SetStation (2010);
	
	private final int value;
	
	private RPC(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return this.name() + "(" + this.value + ")";
	}

	public static RPC getEnumByValue(int value) {
		for(RPC element : RPC.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
