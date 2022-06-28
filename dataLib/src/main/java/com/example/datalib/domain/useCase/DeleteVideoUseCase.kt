package com.example.datalib.domain.useCase

import com.example.datalib.base.BaseUseCaseWithInput
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.domain.repositories.history.VideoHistoryRepo
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import javax.inject.Inject

class DeleteVideoUseCase @Inject constructor(
    private val repo: VideoHistoryRepo
): BaseUseCaseWithInput<PexelsVideoMapedData, Either<Boolean, Failure>> {
    override suspend fun process(input: PexelsVideoMapedData): Either<Boolean, Failure> {
        return repo.deleteVideoHistory(input)
    }
}