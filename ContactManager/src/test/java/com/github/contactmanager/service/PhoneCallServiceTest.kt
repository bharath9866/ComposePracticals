package com.github.contactmanager.service

@RunWith(RobolectricTestRunner::class)
class PhoneCallServiceTest {

    @Mock
    private lateinit var context: Context

    private lateinit var phoneCallService: PhoneCallService
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        phoneCallService = PhoneCallService()
        
        // Mock ContextCompat.checkSelfPermission
        val contextCompat = mock(ContextCompat::class.java)
    }
    
    @Test
    fun `initiatePhoneCall with permission starts call activity`() {
        // Given: the CALL_PHONE permission is granted
        mockPermissionGranted()
        
        // When: initiating a phone call
        val result = phoneCallService.initiatePhoneCall(context, "+1234567890")
        
        // Then: it should return true and start the call activity
        assert(result)
        val intentCaptor = ArgumentCaptor.forClass(Intent::class.java)
        verify(context).startActivity(intentCaptor.capture())
        
        val capturedIntent = intentCaptor.value
        assert(capturedIntent.action == Intent.ACTION_CALL)
        assert(capturedIntent.data == Uri.parse("tel:+1234567890"))
        assert(capturedIntent.flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0)
    }
    
    @Test
    fun `initiatePhoneCall without permission returns false`() {
        // Given: the CALL_PHONE permission is denied
        mockPermissionDenied()
        
        // When: initiating a phone call
        val result = phoneCallService.initiatePhoneCall(context, "+1234567890")
        
        // Then: it should return false and not start any activity
        assert(!result)
        verify(context, never()).startActivity(any(Intent::class.java))
    }
    
    @Test
    fun `openDialer opens dialer with phone number`() {
        // When: opening the dialer
        phoneCallService.openDialer(context, "+1234567890")
        
        // Then: it should start the dialer activity
        val intentCaptor = ArgumentCaptor.forClass(Intent::class.java)
        verify(context).startActivity(intentCaptor.capture())
        
        val capturedIntent = intentCaptor.value
        assert(capturedIntent.action == Intent.ACTION_DIAL)
        assert(capturedIntent.data == Uri.parse("tel:+1234567890"))
        assert(capturedIntent.flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0)
    }
    
    private fun mockPermissionGranted() {
        doReturn(PackageManager.PERMISSION_GRANTED)
            .`when`(ContextCompat.checkSelfPermission(
                eq(context),
                eq(android.Manifest.permission.CALL_PHONE)
            ))
    }
    
    private fun mockPermissionDenied() {
        doReturn(PackageManager.PERMISSION_DENIED)
            .`when`(ContextCompat.checkSelfPermission(
                eq(context),
                eq(android.Manifest.permission.CALL_PHONE)
            ))
    }
}