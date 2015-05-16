/**
*****************************************************************
*                                                               *
*            ������������� ����������� ����'������� ������      *     
*             ������� ������. ���2. ��������� MPI              *
*                                                               *
* ��������: A = B(MO*MK)*a + min(Z)*E*MR                        *
*                                                               *  
* ����: MessageKeys.java                                     *            
* �����: ��������� ���������                                    *
* �����: ��-21                                                  *
* ����: 23.04.15                                                *
*                                                               *
*****************************************************************
*/
public class MessageKeys {
	static int SEND_RECIVE_B_MK_MR_alfa = 1;
	static int SEND_RECIVE_Z_E_MO = 2;
	static int SEND_RECIVE_RESULT_A = 3;
	static int SEND_RECIVE_B_MK_MR_alfa_TO_k=4;
	static int SEND_RECIVE_MO_Z_E_TO_P_1=5;
	static int SEND_RECIVE_A_TO_0 = 6;
}
