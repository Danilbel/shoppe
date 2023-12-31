package dev.danilbel.backend.service.impl;

import dev.danilbel.backend.dto.address.ShippingAddressDto;
import dev.danilbel.backend.entity.ShippingAddressEntity;
import dev.danilbel.backend.entity.UserEntity;
import dev.danilbel.backend.enums.ShippingAddressStatus;
import dev.danilbel.backend.exception.NotFoundException;
import dev.danilbel.backend.mapper.ShippingAddressMapper;
import dev.danilbel.backend.repository.ShippingAddressRepository;
import dev.danilbel.backend.service.ShippingAddressService;
import dev.danilbel.backend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ShippingAddressServiceImpl implements ShippingAddressService {

    ShippingAddressRepository shippingAddressRepository;

    UserService userService;

    ShippingAddressMapper shippingAddressMapper;

    @Override
    public ShippingAddressEntity getShippingAddressEntityById(String id) {

        ShippingAddressEntity result = shippingAddressRepository.findByIdAndStatus(id, ShippingAddressStatus.ACTIVE).orElseThrow(
                () -> {
                    log.error("IN ShippingAddressServiceImpl.getShippingAddressEntityById - shipping address with id '{}' not found", id);
                    return new NotFoundException(
                            String.format("Shipping address with id '%s' not found", id)
                    );
                }
        );

        log.info("IN ShippingAddressServiceImpl.getShippingAddressEntityById - shipping address: {} found by id '{}'", result, id);
        return result;
    }

    @Override
    public ShippingAddressDto getShippingAddressById(String id) {

        ShippingAddressEntity shippingAddressEntity = getShippingAddressEntityById(id);

        return shippingAddressMapper.map(shippingAddressEntity);
    }

    @Override
    @Transactional
    public List<ShippingAddressDto> getAllShippingAddresses(String userEmail) {

        UserEntity userEntity = userService.getUserEntityByEmail(userEmail);

        Stream<ShippingAddressEntity> shippingAddressEntityStream = shippingAddressRepository
                .streamAllByUserIdAndStatus(userEntity.getId(), ShippingAddressStatus.ACTIVE);

        List<ShippingAddressDto> result = shippingAddressEntityStream
                .map(shippingAddressMapper::map)
                .toList();

        log.info("IN ShippingAddressServiceImpl.getAllShippingAddresses - {} shipping addresses found for user with email '{}'", result.size(), userEmail);

        return result;
    }

    @Override
    public ShippingAddressDto createShippingAddress(String userEmail, ShippingAddressDto shippingAddressDto) {

        UserEntity userEntity = userService.getUserEntityByEmail(userEmail);

        ShippingAddressEntity shippingAddressEntity = shippingAddressMapper.map(shippingAddressDto);

        shippingAddressEntity.setUser(userEntity);

        shippingAddressEntity = shippingAddressRepository.save(shippingAddressEntity);

        log.info("IN ShippingAddressServiceImpl.createShippingAddress - shipping address: {} created for user with email '{}'", shippingAddressEntity, userEmail);
        return shippingAddressMapper.map(shippingAddressEntity);
    }

    @Override
    public void deleteShippingAddress(String id) {

        ShippingAddressEntity shippingAddressEntity = getShippingAddressEntityById(id);

        shippingAddressEntity.setStatus(ShippingAddressStatus.DELETED);

        shippingAddressRepository.save(shippingAddressEntity);

        log.info("IN ShippingAddressServiceImpl.removeShippingAddress - shipping address: {} removed", shippingAddressEntity);
    }
}
