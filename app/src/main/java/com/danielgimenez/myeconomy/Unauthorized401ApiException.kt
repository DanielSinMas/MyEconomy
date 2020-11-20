package com.danielgimenez.myeconomy

import java.io.IOException

class Unauthorized401ApiException(override val message: String?) : IOException()